
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.RequestContext;

public class 混淆入口 {

   public static void main(String[] args) throws FileUploadException {
      RequestContext request = new RequestContext() {
         @Override
         public String getCharacterEncoding() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
         }

         @Override
         public String getContentType() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
         }

         @Override
         public int getContentLength() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
         }

         @Override
         public InputStream getInputStream() throws IOException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
         }
      };
      org.apache.commons.fileupload.disk.DiskFileItemFactory factory = new org.apache.commons.fileupload.disk.DiskFileItemFactory();
      org.apache.commons.fileupload.servlet.ServletFileUpload upload = new org.apache.commons.fileupload.servlet.ServletFileUpload(factory);
      upload.parseRequest(request);
      List<org.apache.commons.fileupload.FileItem> items = upload.parseRequest(request);// 得到所有的文件  
      Iterator<org.apache.commons.fileupload.FileItem> i = items.iterator();
      org.apache.commons.fileupload.FileItem fi = (org.apache.commons.fileupload.FileItem) i.next();
   }
}