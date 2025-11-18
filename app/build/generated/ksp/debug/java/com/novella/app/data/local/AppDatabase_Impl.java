package com.novella.app.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.novella.app.data.local.dao.BookmarksDao;
import com.novella.app.data.local.dao.BookmarksDao_Impl;
import com.novella.app.data.local.dao.DownloadsDao;
import com.novella.app.data.local.dao.DownloadsDao_Impl;
import com.novella.app.data.local.dao.NovelsDao;
import com.novella.app.data.local.dao.NovelsDao_Impl;
import com.novella.app.data.local.dao.ProgressDao;
import com.novella.app.data.local.dao.ProgressDao_Impl;
import com.novella.app.data.local.dao.PurchasesDao;
import com.novella.app.data.local.dao.PurchasesDao_Impl;
import com.novella.app.data.local.dao.QuotesDao;
import com.novella.app.data.local.dao.QuotesDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile NovelsDao _novelsDao;

  private volatile ProgressDao _progressDao;

  private volatile BookmarksDao _bookmarksDao;

  private volatile QuotesDao _quotesDao;

  private volatile DownloadsDao _downloadsDao;

  private volatile PurchasesDao _purchasesDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `novels` (`id` TEXT NOT NULL, `title` TEXT NOT NULL, `author` TEXT NOT NULL, `language` TEXT NOT NULL, `description` TEXT NOT NULL, `coverUrl` TEXT NOT NULL, `pdfUrl` TEXT NOT NULL, `category` TEXT NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `progress` (`novelId` TEXT NOT NULL, `currentPage` INTEGER NOT NULL, `totalPages` INTEGER NOT NULL, `percent` INTEGER NOT NULL, PRIMARY KEY(`novelId`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `bookmarks` (`id` TEXT NOT NULL, `novelId` TEXT NOT NULL, `page` INTEGER NOT NULL, `note` TEXT, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `quotes` (`id` TEXT NOT NULL, `novelId` TEXT NOT NULL, `page` INTEGER NOT NULL, `text` TEXT NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `downloads` (`novelId` TEXT NOT NULL, `isDownloaded` INTEGER NOT NULL, `localPath` TEXT NOT NULL, PRIMARY KEY(`novelId`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `purchases` (`productId` TEXT NOT NULL, `type` TEXT NOT NULL, `status` TEXT NOT NULL, PRIMARY KEY(`productId`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '7aa0b7b923f9320c9d8ad3adace06c85')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `novels`");
        db.execSQL("DROP TABLE IF EXISTS `progress`");
        db.execSQL("DROP TABLE IF EXISTS `bookmarks`");
        db.execSQL("DROP TABLE IF EXISTS `quotes`");
        db.execSQL("DROP TABLE IF EXISTS `downloads`");
        db.execSQL("DROP TABLE IF EXISTS `purchases`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsNovels = new HashMap<String, TableInfo.Column>(8);
        _columnsNovels.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNovels.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNovels.put("author", new TableInfo.Column("author", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNovels.put("language", new TableInfo.Column("language", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNovels.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNovels.put("coverUrl", new TableInfo.Column("coverUrl", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNovels.put("pdfUrl", new TableInfo.Column("pdfUrl", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNovels.put("category", new TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysNovels = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesNovels = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoNovels = new TableInfo("novels", _columnsNovels, _foreignKeysNovels, _indicesNovels);
        final TableInfo _existingNovels = TableInfo.read(db, "novels");
        if (!_infoNovels.equals(_existingNovels)) {
          return new RoomOpenHelper.ValidationResult(false, "novels(com.novella.app.data.local.entities.NovelEntity).\n"
                  + " Expected:\n" + _infoNovels + "\n"
                  + " Found:\n" + _existingNovels);
        }
        final HashMap<String, TableInfo.Column> _columnsProgress = new HashMap<String, TableInfo.Column>(4);
        _columnsProgress.put("novelId", new TableInfo.Column("novelId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProgress.put("currentPage", new TableInfo.Column("currentPage", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProgress.put("totalPages", new TableInfo.Column("totalPages", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProgress.put("percent", new TableInfo.Column("percent", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysProgress = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesProgress = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoProgress = new TableInfo("progress", _columnsProgress, _foreignKeysProgress, _indicesProgress);
        final TableInfo _existingProgress = TableInfo.read(db, "progress");
        if (!_infoProgress.equals(_existingProgress)) {
          return new RoomOpenHelper.ValidationResult(false, "progress(com.novella.app.data.local.entities.ProgressEntity).\n"
                  + " Expected:\n" + _infoProgress + "\n"
                  + " Found:\n" + _existingProgress);
        }
        final HashMap<String, TableInfo.Column> _columnsBookmarks = new HashMap<String, TableInfo.Column>(4);
        _columnsBookmarks.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBookmarks.put("novelId", new TableInfo.Column("novelId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBookmarks.put("page", new TableInfo.Column("page", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBookmarks.put("note", new TableInfo.Column("note", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysBookmarks = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesBookmarks = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoBookmarks = new TableInfo("bookmarks", _columnsBookmarks, _foreignKeysBookmarks, _indicesBookmarks);
        final TableInfo _existingBookmarks = TableInfo.read(db, "bookmarks");
        if (!_infoBookmarks.equals(_existingBookmarks)) {
          return new RoomOpenHelper.ValidationResult(false, "bookmarks(com.novella.app.data.local.entities.BookmarkEntity).\n"
                  + " Expected:\n" + _infoBookmarks + "\n"
                  + " Found:\n" + _existingBookmarks);
        }
        final HashMap<String, TableInfo.Column> _columnsQuotes = new HashMap<String, TableInfo.Column>(4);
        _columnsQuotes.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuotes.put("novelId", new TableInfo.Column("novelId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuotes.put("page", new TableInfo.Column("page", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuotes.put("text", new TableInfo.Column("text", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysQuotes = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesQuotes = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoQuotes = new TableInfo("quotes", _columnsQuotes, _foreignKeysQuotes, _indicesQuotes);
        final TableInfo _existingQuotes = TableInfo.read(db, "quotes");
        if (!_infoQuotes.equals(_existingQuotes)) {
          return new RoomOpenHelper.ValidationResult(false, "quotes(com.novella.app.data.local.entities.QuoteEntity).\n"
                  + " Expected:\n" + _infoQuotes + "\n"
                  + " Found:\n" + _existingQuotes);
        }
        final HashMap<String, TableInfo.Column> _columnsDownloads = new HashMap<String, TableInfo.Column>(3);
        _columnsDownloads.put("novelId", new TableInfo.Column("novelId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDownloads.put("isDownloaded", new TableInfo.Column("isDownloaded", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDownloads.put("localPath", new TableInfo.Column("localPath", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysDownloads = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesDownloads = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoDownloads = new TableInfo("downloads", _columnsDownloads, _foreignKeysDownloads, _indicesDownloads);
        final TableInfo _existingDownloads = TableInfo.read(db, "downloads");
        if (!_infoDownloads.equals(_existingDownloads)) {
          return new RoomOpenHelper.ValidationResult(false, "downloads(com.novella.app.data.local.entities.DownloadEntity).\n"
                  + " Expected:\n" + _infoDownloads + "\n"
                  + " Found:\n" + _existingDownloads);
        }
        final HashMap<String, TableInfo.Column> _columnsPurchases = new HashMap<String, TableInfo.Column>(3);
        _columnsPurchases.put("productId", new TableInfo.Column("productId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPurchases.put("type", new TableInfo.Column("type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPurchases.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPurchases = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPurchases = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPurchases = new TableInfo("purchases", _columnsPurchases, _foreignKeysPurchases, _indicesPurchases);
        final TableInfo _existingPurchases = TableInfo.read(db, "purchases");
        if (!_infoPurchases.equals(_existingPurchases)) {
          return new RoomOpenHelper.ValidationResult(false, "purchases(com.novella.app.data.local.entities.PurchaseEntity).\n"
                  + " Expected:\n" + _infoPurchases + "\n"
                  + " Found:\n" + _existingPurchases);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "7aa0b7b923f9320c9d8ad3adace06c85", "7e10b8345e6fdcee4f3738bfa4a57d76");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "novels","progress","bookmarks","quotes","downloads","purchases");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `novels`");
      _db.execSQL("DELETE FROM `progress`");
      _db.execSQL("DELETE FROM `bookmarks`");
      _db.execSQL("DELETE FROM `quotes`");
      _db.execSQL("DELETE FROM `downloads`");
      _db.execSQL("DELETE FROM `purchases`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(NovelsDao.class, NovelsDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ProgressDao.class, ProgressDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(BookmarksDao.class, BookmarksDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(QuotesDao.class, QuotesDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(DownloadsDao.class, DownloadsDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(PurchasesDao.class, PurchasesDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public NovelsDao novelsDao() {
    if (_novelsDao != null) {
      return _novelsDao;
    } else {
      synchronized(this) {
        if(_novelsDao == null) {
          _novelsDao = new NovelsDao_Impl(this);
        }
        return _novelsDao;
      }
    }
  }

  @Override
  public ProgressDao progressDao() {
    if (_progressDao != null) {
      return _progressDao;
    } else {
      synchronized(this) {
        if(_progressDao == null) {
          _progressDao = new ProgressDao_Impl(this);
        }
        return _progressDao;
      }
    }
  }

  @Override
  public BookmarksDao bookmarksDao() {
    if (_bookmarksDao != null) {
      return _bookmarksDao;
    } else {
      synchronized(this) {
        if(_bookmarksDao == null) {
          _bookmarksDao = new BookmarksDao_Impl(this);
        }
        return _bookmarksDao;
      }
    }
  }

  @Override
  public QuotesDao quotesDao() {
    if (_quotesDao != null) {
      return _quotesDao;
    } else {
      synchronized(this) {
        if(_quotesDao == null) {
          _quotesDao = new QuotesDao_Impl(this);
        }
        return _quotesDao;
      }
    }
  }

  @Override
  public DownloadsDao downloadsDao() {
    if (_downloadsDao != null) {
      return _downloadsDao;
    } else {
      synchronized(this) {
        if(_downloadsDao == null) {
          _downloadsDao = new DownloadsDao_Impl(this);
        }
        return _downloadsDao;
      }
    }
  }

  @Override
  public PurchasesDao purchasesDao() {
    if (_purchasesDao != null) {
      return _purchasesDao;
    } else {
      synchronized(this) {
        if(_purchasesDao == null) {
          _purchasesDao = new PurchasesDao_Impl(this);
        }
        return _purchasesDao;
      }
    }
  }
}
