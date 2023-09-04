package org.wumoe.devore.rocksdb;

import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.wumoe.devore.Devore;
import org.wumoe.devore.plugins.DPlugin;

public class RocksDBPlugin extends DPlugin {

    @Override
    public void onEnable() {
        RocksDB.loadLibrary();
        RocksDBModule.options = new Options();
        RocksDBModule.options.setCreateIfMissing(true);
        Devore.addModule("rocksdb", new RocksDBModule());
    }

    @Override
    public void onDisable() {
    }
}
