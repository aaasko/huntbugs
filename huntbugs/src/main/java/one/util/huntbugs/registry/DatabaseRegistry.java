/*
 * Copyright 2016 HuntBugs contributors
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package one.util.huntbugs.registry;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.strobel.assembler.metadata.TypeDefinition;
import com.strobel.assembler.metadata.TypeReference;

import one.util.huntbugs.analysis.Context;
import one.util.huntbugs.registry.anno.TypeDatabase;
import one.util.huntbugs.registry.anno.TypeDatabaseItem;

/**
 * @author Tagir Valeev
 *
 */
public class DatabaseRegistry {
    Context ctx;
    Map<Class<?>, DatabaseInfo<?>> instances = new HashMap<>();

    static class DatabaseInfo<T> {
        final T db;
        final AbstractTypeDatabase<T> parentDb;

        public DatabaseInfo(T db, AbstractTypeDatabase<T> parentDb) {
            this.db = db;
            this.parentDb = parentDb;
        }

        public T getDatabase(TypeReference tr) {
            if (db != null)
                return db;
            return parentDb.get(tr);
        }
    }

    public DatabaseRegistry(Context ctx) {
        super();
        this.ctx = ctx;
    }

    public <T> Function<TypeReference, T> queryDatabase(Class<T> clazz) {
        return getDatabaseInfo(clazz)::getDatabase;
    }
    
    void visitType(TypeDefinition td) {
        for(DatabaseInfo<?> dbi : instances.values()) {
            Object db = dbi.db;
            if(db instanceof AbstractTypeDatabase) {
                ((AbstractTypeDatabase<?>) db).visitType(td);
            }
        }
    }

    private <T> DatabaseInfo<T> getDatabaseInfo(Class<T> clazz) {
        // Cannot use computeIfAbsent here as recursive update is unsafe
        @SuppressWarnings("unchecked")
        DatabaseInfo<T> di = (DatabaseInfo<T>) instances.get(clazz);
        if(di == null) {
            di = resolveDatabase(clazz);
            instances.put(clazz, di);
        }
        return di;
    }

    private <T> DatabaseInfo<T> resolveDatabase(Class<T> clazz) {
        ctx.incStat("Databases");
        TypeDatabase td = clazz.getAnnotation(TypeDatabase.class);
        TypeDatabaseItem tdi = clazz.getAnnotation(TypeDatabaseItem.class);
        if (td != null && tdi != null) {
            throw new InternalError("Database " + clazz + " is annotated both as " + TypeDatabase.class.getSimpleName()
                + " and " + TypeDatabaseItem.class.getSimpleName() + ". Remove one of the annotations!");
        }
        if (td == null && tdi == null) {
            throw new IllegalStateException("Unknown database requested: " + clazz + " (not annotated as "
                + TypeDatabase.class.getSimpleName() + " or " + TypeDatabaseItem.class.getSimpleName()+")");
        }
        if (td != null) {
            try {
                return new DatabaseInfo<>(clazz.newInstance(), null);
            } catch (InstantiationException | IllegalAccessException e) {
                throw new IllegalStateException("Unable to instantiate database " + clazz, e);
            }
        }
        @SuppressWarnings("unchecked")
        DatabaseInfo<? extends AbstractTypeDatabase<T>> parentInfo = getDatabaseInfo((Class<AbstractTypeDatabase<T>>) tdi
                .parentDatabase());
        return new DatabaseInfo<T>(null, parentInfo.db);
    }
}
