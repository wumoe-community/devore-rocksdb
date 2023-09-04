package org.wumoe.devore.rocksdb;

import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.wumoe.devore.exception.DevoreRuntimeException;
import org.wumoe.devore.lang.token.DBool;
import org.wumoe.devore.lang.token.DString;
import org.wumoe.devore.lang.token.DWord;
import org.wumoe.devore.lang.token.Token;

import java.nio.charset.StandardCharsets;

public class DRocksDB extends Token {
    private final RocksDB db;

    public void put(Token key, Token value) {
        try {
            db.put(key.toString().getBytes(StandardCharsets.UTF_8), value.toString().getBytes(StandardCharsets.UTF_8));
        } catch (RocksDBException e) {
            throw new DevoreRuntimeException(e.getMessage());
        }
    }

    public Token get(Token key) {
        try {
            byte[] result = db.get(key.toString().getBytes(StandardCharsets.UTF_8));
            if (result == null)
                return DWord.WORD_NIL;
            return DString.valueOf(new String(result, StandardCharsets.UTF_8));
        } catch (RocksDBException e) {
            throw new DevoreRuntimeException(e.getMessage());
        }
    }

    public void delete(Token key) {
        try {
            db.delete(key.toString().getBytes(StandardCharsets.UTF_8));
        } catch (RocksDBException e) {
            throw new DevoreRuntimeException(e.getMessage());
        }
    }

    public DRocksDB(RocksDB db) {
        this.db = db;
    }

    @Override
    public String type() {
        return "rocksdb";
    }

    @Override
    public String str() {
        return db.toString();
    }

    @Override
    public Token copy() {
        return new DRocksDB(db);
    }

    @Override
    public int compareTo(Token token) {
        return -1;
    }
}
