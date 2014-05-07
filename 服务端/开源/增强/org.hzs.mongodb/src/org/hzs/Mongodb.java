package org.hzs;

import java.io.IOException;
import java.net.UnknownHostException;

public class Mongodb {

    private com.mongodb.DB db = null;

    public Mongodb(final String ci网址_s, final int ci端口_i, final String ci数据库_s) throws UnknownHostException {
        com.mongodb.MongoClient mongo = new com.mongodb.MongoClient(ci网址_s, ci端口_i);
        db = mongo.getDB(ci数据库_s);
    }

    public void g保存文档(final org.hzs.lang.ID id, final byte[] byteFile, final org.hzs.logging.error ci_error) throws IOException, org.hzs.logging.error {
        com.mongodb.gridfs.GridFS fs = null;
        java.io.ByteArrayInputStream input = null;
        com.mongodb.gridfs.GridFSInputFile fsFile = null;
        try {
            fs = new com.mongodb.gridfs.GridFS(db, "fs");
            input = new java.io.ByteArrayInputStream(byteFile);
            fsFile = fs.createFile(input);
            fsFile.setFilename(id.toString());
            fsFile.save();
        } finally {
            fs = null;
            fsFile = null;
            if (input != null) {
                input.close();
                input = null;
            }
        }
    }

    public void g删除文档(final org.hzs.lang.ID id, final org.hzs.logging.error ci_error) throws org.hzs.logging.error {
        com.mongodb.gridfs.GridFS fs = null;
        try {
            fs = new com.mongodb.gridfs.GridFS(db);
            fs.remove(id.toString());
        } finally {
            fs = null;
        }
    }

    public byte[] i读取文档_byteArray(final org.hzs.lang.ID id, final org.hzs.logging.error ci_error) throws IOException, org.hzs.logging.error {
        com.mongodb.gridfs.GridFS fs = null;
        com.mongodb.gridfs.GridFSDBFile user = null;
        java.io.InputStream input = null;
        byte[] b = null;
        try {
            fs = new com.mongodb.gridfs.GridFS(db);
            user = fs.findOne(id.toString());
            input = user.getInputStream();
            b = new byte[(int) user.getLength()];
            while (input.read(b) > 0) {
                input.read(b);
            }
            return b;
        } finally {
            fs = null;
            user = null;
            b = null;
            if (input != null) {
                input.close();
                input = null;
            }
        }
    }
}
