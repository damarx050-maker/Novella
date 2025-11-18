package com.novella.app.data.local.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.novella.app.data.local.entities.ProgressEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ProgressDao_Impl implements ProgressDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ProgressEntity> __insertionAdapterOfProgressEntity;

  public ProgressDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfProgressEntity = new EntityInsertionAdapter<ProgressEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `progress` (`novelId`,`currentPage`,`totalPages`,`percent`) VALUES (?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ProgressEntity entity) {
        statement.bindString(1, entity.getNovelId());
        statement.bindLong(2, entity.getCurrentPage());
        statement.bindLong(3, entity.getTotalPages());
        statement.bindLong(4, entity.getPercent());
      }
    };
  }

  @Override
  public Object upsert(final ProgressEntity progress,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfProgressEntity.insert(progress);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<ProgressEntity> observeProgress(final String novelId) {
    final String _sql = "SELECT * FROM progress WHERE novelId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, novelId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"progress"}, new Callable<ProgressEntity>() {
      @Override
      @Nullable
      public ProgressEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfNovelId = CursorUtil.getColumnIndexOrThrow(_cursor, "novelId");
          final int _cursorIndexOfCurrentPage = CursorUtil.getColumnIndexOrThrow(_cursor, "currentPage");
          final int _cursorIndexOfTotalPages = CursorUtil.getColumnIndexOrThrow(_cursor, "totalPages");
          final int _cursorIndexOfPercent = CursorUtil.getColumnIndexOrThrow(_cursor, "percent");
          final ProgressEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpNovelId;
            _tmpNovelId = _cursor.getString(_cursorIndexOfNovelId);
            final int _tmpCurrentPage;
            _tmpCurrentPage = _cursor.getInt(_cursorIndexOfCurrentPage);
            final int _tmpTotalPages;
            _tmpTotalPages = _cursor.getInt(_cursorIndexOfTotalPages);
            final int _tmpPercent;
            _tmpPercent = _cursor.getInt(_cursorIndexOfPercent);
            _result = new ProgressEntity(_tmpNovelId,_tmpCurrentPage,_tmpTotalPages,_tmpPercent);
          } else {
            _result = null;
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
