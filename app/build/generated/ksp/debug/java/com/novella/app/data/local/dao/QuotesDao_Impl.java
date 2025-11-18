package com.novella.app.data.local.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.novella.app.data.local.entities.QuoteEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class QuotesDao_Impl implements QuotesDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<QuoteEntity> __insertionAdapterOfQuoteEntity;

  private final EntityDeletionOrUpdateAdapter<QuoteEntity> __deletionAdapterOfQuoteEntity;

  public QuotesDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfQuoteEntity = new EntityInsertionAdapter<QuoteEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `quotes` (`id`,`novelId`,`page`,`text`) VALUES (?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final QuoteEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getNovelId());
        statement.bindLong(3, entity.getPage());
        statement.bindString(4, entity.getText());
      }
    };
    this.__deletionAdapterOfQuoteEntity = new EntityDeletionOrUpdateAdapter<QuoteEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `quotes` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final QuoteEntity entity) {
        statement.bindString(1, entity.getId());
      }
    };
  }

  @Override
  public Object add(final QuoteEntity quote, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfQuoteEntity.insert(quote);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object remove(final QuoteEntity quote, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfQuoteEntity.handle(quote);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<QuoteEntity>> list(final String novelId) {
    final String _sql = "SELECT * FROM quotes WHERE novelId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, novelId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"quotes"}, new Callable<List<QuoteEntity>>() {
      @Override
      @NonNull
      public List<QuoteEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNovelId = CursorUtil.getColumnIndexOrThrow(_cursor, "novelId");
          final int _cursorIndexOfPage = CursorUtil.getColumnIndexOrThrow(_cursor, "page");
          final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
          final List<QuoteEntity> _result = new ArrayList<QuoteEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final QuoteEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpNovelId;
            _tmpNovelId = _cursor.getString(_cursorIndexOfNovelId);
            final int _tmpPage;
            _tmpPage = _cursor.getInt(_cursorIndexOfPage);
            final String _tmpText;
            _tmpText = _cursor.getString(_cursorIndexOfText);
            _item = new QuoteEntity(_tmpId,_tmpNovelId,_tmpPage,_tmpText);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
