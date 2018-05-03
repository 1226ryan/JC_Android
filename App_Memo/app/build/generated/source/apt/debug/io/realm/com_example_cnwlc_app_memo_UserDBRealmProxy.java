package io.realm;


import android.annotation.TargetApi;
import android.os.Build;
import android.util.JsonReader;
import android.util.JsonToken;
import io.realm.ProxyUtils;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.ColumnInfo;
import io.realm.internal.OsList;
import io.realm.internal.OsObject;
import io.realm.internal.OsObjectSchemaInfo;
import io.realm.internal.OsSchemaInfo;
import io.realm.internal.Property;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Row;
import io.realm.internal.Table;
import io.realm.internal.android.JsonUtils;
import io.realm.log.RealmLog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("all")
public class com_example_cnwlc_app_memo_UserDBRealmProxy extends com.example.cnwlc.app_memo.UserDB
    implements RealmObjectProxy, com_example_cnwlc_app_memo_UserDBRealmProxyInterface {

    static final class UserDBColumnInfo extends ColumnInfo {
        long nameIndex;
        long ageIndex;

        UserDBColumnInfo(OsSchemaInfo schemaInfo) {
            super(2);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("UserDB");
            this.nameIndex = addColumnDetails("name", "name", objectSchemaInfo);
            this.ageIndex = addColumnDetails("age", "age", objectSchemaInfo);
        }

        UserDBColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new UserDBColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final UserDBColumnInfo src = (UserDBColumnInfo) rawSrc;
            final UserDBColumnInfo dst = (UserDBColumnInfo) rawDst;
            dst.nameIndex = src.nameIndex;
            dst.ageIndex = src.ageIndex;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();

    private UserDBColumnInfo columnInfo;
    private ProxyState<com.example.cnwlc.app_memo.UserDB> proxyState;

    com_example_cnwlc_app_memo_UserDBRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (UserDBColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.example.cnwlc.app_memo.UserDB>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$name() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.nameIndex);
    }

    @Override
    public void realmSet$name(String value) {
        if (proxyState.isUnderConstruction()) {
            // default value of the primary key is always ignored.
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        throw new io.realm.exceptions.RealmException("Primary key field 'name' cannot be changed after object was created.");
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$age() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.ageIndex);
    }

    @Override
    public void realmSet$age(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.ageIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.ageIndex, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("UserDB", 2, 0);
        builder.addPersistedProperty("name", RealmFieldType.STRING, Property.PRIMARY_KEY, Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("age", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static UserDBColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new UserDBColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "UserDB";
    }

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "UserDB";
    }

    @SuppressWarnings("cast")
    public static com.example.cnwlc.app_memo.UserDB createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.example.cnwlc.app_memo.UserDB obj = null;
        if (update) {
            Table table = realm.getTable(com.example.cnwlc.app_memo.UserDB.class);
            UserDBColumnInfo columnInfo = (UserDBColumnInfo) realm.getSchema().getColumnInfo(com.example.cnwlc.app_memo.UserDB.class);
            long pkColumnIndex = columnInfo.nameIndex;
            long rowIndex = Table.NO_MATCH;
            if (json.isNull("name")) {
                rowIndex = table.findFirstNull(pkColumnIndex);
            } else {
                rowIndex = table.findFirstString(pkColumnIndex, json.getString("name"));
            }
            if (rowIndex != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.example.cnwlc.app_memo.UserDB.class), false, Collections.<String> emptyList());
                    obj = new io.realm.com_example_cnwlc_app_memo_UserDBRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("name")) {
                if (json.isNull("name")) {
                    obj = (io.realm.com_example_cnwlc_app_memo_UserDBRealmProxy) realm.createObjectInternal(com.example.cnwlc.app_memo.UserDB.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.com_example_cnwlc_app_memo_UserDBRealmProxy) realm.createObjectInternal(com.example.cnwlc.app_memo.UserDB.class, json.getString("name"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'name'.");
            }
        }

        final com_example_cnwlc_app_memo_UserDBRealmProxyInterface objProxy = (com_example_cnwlc_app_memo_UserDBRealmProxyInterface) obj;
        if (json.has("age")) {
            if (json.isNull("age")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'age' to null.");
            } else {
                objProxy.realmSet$age((int) json.getInt("age"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.example.cnwlc.app_memo.UserDB createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        final com.example.cnwlc.app_memo.UserDB obj = new com.example.cnwlc.app_memo.UserDB();
        final com_example_cnwlc_app_memo_UserDBRealmProxyInterface objProxy = (com_example_cnwlc_app_memo_UserDBRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("name")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$name((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$name(null);
                }
                jsonHasPrimaryKey = true;
            } else if (name.equals("age")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$age((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'age' to null.");
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        if (!jsonHasPrimaryKey) {
            throw new IllegalArgumentException("JSON object doesn't have the primary key field 'name'.");
        }
        return realm.copyToRealm(obj);
    }

    public static com.example.cnwlc.app_memo.UserDB copyOrUpdate(Realm realm, com.example.cnwlc.app_memo.UserDB object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null) {
            final BaseRealm otherRealm = ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm();
            if (otherRealm.threadId != realm.threadId) {
                throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
            }
            if (otherRealm.getPath().equals(realm.getPath())) {
                return object;
            }
        }
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        RealmObjectProxy cachedRealmObject = cache.get(object);
        if (cachedRealmObject != null) {
            return (com.example.cnwlc.app_memo.UserDB) cachedRealmObject;
        }

        com.example.cnwlc.app_memo.UserDB realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(com.example.cnwlc.app_memo.UserDB.class);
            UserDBColumnInfo columnInfo = (UserDBColumnInfo) realm.getSchema().getColumnInfo(com.example.cnwlc.app_memo.UserDB.class);
            long pkColumnIndex = columnInfo.nameIndex;
            String value = ((com_example_cnwlc_app_memo_UserDBRealmProxyInterface) object).realmGet$name();
            long rowIndex = Table.NO_MATCH;
            if (value == null) {
                rowIndex = table.findFirstNull(pkColumnIndex);
            } else {
                rowIndex = table.findFirstString(pkColumnIndex, value);
            }
            if (rowIndex == Table.NO_MATCH) {
                canUpdate = false;
            } else {
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.example.cnwlc.app_memo.UserDB.class), false, Collections.<String> emptyList());
                    realmObject = new io.realm.com_example_cnwlc_app_memo_UserDBRealmProxy();
                    cache.put(object, (RealmObjectProxy) realmObject);
                } finally {
                    objectContext.clear();
                }
            }
        }

        return (canUpdate) ? update(realm, realmObject, object, cache) : copy(realm, object, update, cache);
    }

    public static com.example.cnwlc.app_memo.UserDB copy(Realm realm, com.example.cnwlc.app_memo.UserDB newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.example.cnwlc.app_memo.UserDB) cachedRealmObject;
        }

        // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
        com.example.cnwlc.app_memo.UserDB realmObject = realm.createObjectInternal(com.example.cnwlc.app_memo.UserDB.class, ((com_example_cnwlc_app_memo_UserDBRealmProxyInterface) newObject).realmGet$name(), false, Collections.<String>emptyList());
        cache.put(newObject, (RealmObjectProxy) realmObject);

        com_example_cnwlc_app_memo_UserDBRealmProxyInterface realmObjectSource = (com_example_cnwlc_app_memo_UserDBRealmProxyInterface) newObject;
        com_example_cnwlc_app_memo_UserDBRealmProxyInterface realmObjectCopy = (com_example_cnwlc_app_memo_UserDBRealmProxyInterface) realmObject;

        realmObjectCopy.realmSet$age(realmObjectSource.realmGet$age());
        return realmObject;
    }

    public static long insert(Realm realm, com.example.cnwlc.app_memo.UserDB object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.example.cnwlc.app_memo.UserDB.class);
        long tableNativePtr = table.getNativePtr();
        UserDBColumnInfo columnInfo = (UserDBColumnInfo) realm.getSchema().getColumnInfo(com.example.cnwlc.app_memo.UserDB.class);
        long pkColumnIndex = columnInfo.nameIndex;
        String primaryKeyValue = ((com_example_cnwlc_app_memo_UserDBRealmProxyInterface) object).realmGet$name();
        long rowIndex = Table.NO_MATCH;
        if (primaryKeyValue == null) {
            rowIndex = Table.nativeFindFirstNull(tableNativePtr, pkColumnIndex);
        } else {
            rowIndex = Table.nativeFindFirstString(tableNativePtr, pkColumnIndex, primaryKeyValue);
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, primaryKeyValue);
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, rowIndex);
        Table.nativeSetLong(tableNativePtr, columnInfo.ageIndex, rowIndex, ((com_example_cnwlc_app_memo_UserDBRealmProxyInterface) object).realmGet$age(), false);
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.example.cnwlc.app_memo.UserDB.class);
        long tableNativePtr = table.getNativePtr();
        UserDBColumnInfo columnInfo = (UserDBColumnInfo) realm.getSchema().getColumnInfo(com.example.cnwlc.app_memo.UserDB.class);
        long pkColumnIndex = columnInfo.nameIndex;
        com.example.cnwlc.app_memo.UserDB object = null;
        while (objects.hasNext()) {
            object = (com.example.cnwlc.app_memo.UserDB) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            String primaryKeyValue = ((com_example_cnwlc_app_memo_UserDBRealmProxyInterface) object).realmGet$name();
            long rowIndex = Table.NO_MATCH;
            if (primaryKeyValue == null) {
                rowIndex = Table.nativeFindFirstNull(tableNativePtr, pkColumnIndex);
            } else {
                rowIndex = Table.nativeFindFirstString(tableNativePtr, pkColumnIndex, primaryKeyValue);
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, primaryKeyValue);
            } else {
                Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
            }
            cache.put(object, rowIndex);
            Table.nativeSetLong(tableNativePtr, columnInfo.ageIndex, rowIndex, ((com_example_cnwlc_app_memo_UserDBRealmProxyInterface) object).realmGet$age(), false);
        }
    }

    public static long insertOrUpdate(Realm realm, com.example.cnwlc.app_memo.UserDB object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.example.cnwlc.app_memo.UserDB.class);
        long tableNativePtr = table.getNativePtr();
        UserDBColumnInfo columnInfo = (UserDBColumnInfo) realm.getSchema().getColumnInfo(com.example.cnwlc.app_memo.UserDB.class);
        long pkColumnIndex = columnInfo.nameIndex;
        String primaryKeyValue = ((com_example_cnwlc_app_memo_UserDBRealmProxyInterface) object).realmGet$name();
        long rowIndex = Table.NO_MATCH;
        if (primaryKeyValue == null) {
            rowIndex = Table.nativeFindFirstNull(tableNativePtr, pkColumnIndex);
        } else {
            rowIndex = Table.nativeFindFirstString(tableNativePtr, pkColumnIndex, primaryKeyValue);
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, primaryKeyValue);
        }
        cache.put(object, rowIndex);
        Table.nativeSetLong(tableNativePtr, columnInfo.ageIndex, rowIndex, ((com_example_cnwlc_app_memo_UserDBRealmProxyInterface) object).realmGet$age(), false);
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.example.cnwlc.app_memo.UserDB.class);
        long tableNativePtr = table.getNativePtr();
        UserDBColumnInfo columnInfo = (UserDBColumnInfo) realm.getSchema().getColumnInfo(com.example.cnwlc.app_memo.UserDB.class);
        long pkColumnIndex = columnInfo.nameIndex;
        com.example.cnwlc.app_memo.UserDB object = null;
        while (objects.hasNext()) {
            object = (com.example.cnwlc.app_memo.UserDB) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            String primaryKeyValue = ((com_example_cnwlc_app_memo_UserDBRealmProxyInterface) object).realmGet$name();
            long rowIndex = Table.NO_MATCH;
            if (primaryKeyValue == null) {
                rowIndex = Table.nativeFindFirstNull(tableNativePtr, pkColumnIndex);
            } else {
                rowIndex = Table.nativeFindFirstString(tableNativePtr, pkColumnIndex, primaryKeyValue);
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, primaryKeyValue);
            }
            cache.put(object, rowIndex);
            Table.nativeSetLong(tableNativePtr, columnInfo.ageIndex, rowIndex, ((com_example_cnwlc_app_memo_UserDBRealmProxyInterface) object).realmGet$age(), false);
        }
    }

    public static com.example.cnwlc.app_memo.UserDB createDetachedCopy(com.example.cnwlc.app_memo.UserDB realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.example.cnwlc.app_memo.UserDB unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.example.cnwlc.app_memo.UserDB();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.example.cnwlc.app_memo.UserDB) cachedObject.object;
            }
            unmanagedObject = (com.example.cnwlc.app_memo.UserDB) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        com_example_cnwlc_app_memo_UserDBRealmProxyInterface unmanagedCopy = (com_example_cnwlc_app_memo_UserDBRealmProxyInterface) unmanagedObject;
        com_example_cnwlc_app_memo_UserDBRealmProxyInterface realmSource = (com_example_cnwlc_app_memo_UserDBRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$name(realmSource.realmGet$name());
        unmanagedCopy.realmSet$age(realmSource.realmGet$age());

        return unmanagedObject;
    }

    static com.example.cnwlc.app_memo.UserDB update(Realm realm, com.example.cnwlc.app_memo.UserDB realmObject, com.example.cnwlc.app_memo.UserDB newObject, Map<RealmModel, RealmObjectProxy> cache) {
        com_example_cnwlc_app_memo_UserDBRealmProxyInterface realmObjectTarget = (com_example_cnwlc_app_memo_UserDBRealmProxyInterface) realmObject;
        com_example_cnwlc_app_memo_UserDBRealmProxyInterface realmObjectSource = (com_example_cnwlc_app_memo_UserDBRealmProxyInterface) newObject;
        realmObjectTarget.realmSet$age(realmObjectSource.realmGet$age());
        return realmObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("UserDB = proxy[");
        stringBuilder.append("{name:");
        stringBuilder.append(realmGet$name() != null ? realmGet$name() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{age:");
        stringBuilder.append(realmGet$age());
        stringBuilder.append("}");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public ProxyState<?> realmGet$proxyState() {
        return proxyState;
    }

    @Override
    public int hashCode() {
        String realmName = proxyState.getRealm$realm().getPath();
        String tableName = proxyState.getRow$realm().getTable().getName();
        long rowIndex = proxyState.getRow$realm().getIndex();

        int result = 17;
        result = 31 * result + ((realmName != null) ? realmName.hashCode() : 0);
        result = 31 * result + ((tableName != null) ? tableName.hashCode() : 0);
        result = 31 * result + (int) (rowIndex ^ (rowIndex >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        com_example_cnwlc_app_memo_UserDBRealmProxy aUserDB = (com_example_cnwlc_app_memo_UserDBRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aUserDB.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aUserDB.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aUserDB.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }
}
