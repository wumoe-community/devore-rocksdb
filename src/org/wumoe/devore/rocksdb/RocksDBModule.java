package org.wumoe.devore.rocksdb;

import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.wumoe.devore.exception.DevoreCastException;
import org.wumoe.devore.exception.DevoreRuntimeException;
import org.wumoe.devore.lang.Env;
import org.wumoe.devore.lang.token.DWord;
import org.wumoe.devore.module.Module;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RocksDBModule extends Module {
    public static Options options;

    @Override
    public void init(Env dEnv) {
        dEnv.addTokenFunction("rocksdb-open", (args, env) -> {
            Path path = Paths.get(args.get(0).toString());
            try {
                if (!Files.isSymbolicLink(path))
                    Files.createDirectories(path);
                return new DRocksDB(RocksDB.open(options, args.get(0).toString()));
            } catch (RocksDBException | IOException e) {
                throw new DevoreRuntimeException(e.getMessage());
            }
        }, 1, false);
        dEnv.addTokenFunction("rocksdb-put", (args, env) -> {
            if (!(args.get(0) instanceof DRocksDB db))
                throw new DevoreCastException(args.get(0).type(), "rocksdb");
            db.put(args.get(1), args.get(2));
            return DWord.WORD_NIL;
        }, 3, false);
        dEnv.addTokenFunction("rocksdb-get", (args, env) -> {
            if (!(args.get(0) instanceof DRocksDB db))
                throw new DevoreCastException(args.get(0).type(), "rocksdb");
            return db.get(args.get(1));
        }, 2, false);
        dEnv.addTokenFunction("rocksdb-delete", (args, env) -> {
            if (!(args.get(0) instanceof DRocksDB db))
                throw new DevoreCastException(args.get(0).type(), "rocksdb");
            db.delete(args.get(1));
            return DWord.WORD_NIL;
        }, 2, false);
    }
}
