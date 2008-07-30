package org.apache.solr.handler.dataimport;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Test for FileListEntityProcessor
 * </p>
 *
 * @version $Id$
 * @since solr 1.3
 */
public class TestFileListEntityProcessor {

  @Test
  @SuppressWarnings("unchecked")
  public void testSimple() throws IOException {
    long time = System.currentTimeMillis();
    File tmpdir = new File("." + time);
    tmpdir.mkdir();
    tmpdir.deleteOnExit();
    createFile(tmpdir, "a.xml", "a.xml".getBytes(), false);
    createFile(tmpdir, "b.xml", "b.xml".getBytes(), false);
    createFile(tmpdir, "c.props", "c.props".getBytes(), false);
    Map attrs = AbstractDataImportHandlerTest.createMap(
            FileListEntityProcessor.FILE_NAME, "xml$",
            FileListEntityProcessor.BASE_DIR, tmpdir.getAbsolutePath());
    Context c = AbstractDataImportHandlerTest.getContext(null,
            new VariableResolverImpl(), null, 0, Collections.EMPTY_LIST, attrs);
    FileListEntityProcessor fileListEntityProcessor = new FileListEntityProcessor();
    fileListEntityProcessor.init(c);
    List<String> fList = new ArrayList<String>();
    while (true) {
      Map<String, Object> f = fileListEntityProcessor.nextRow();
      if (f == null)
        break;
      fList.add((String) f.get(FileListEntityProcessor.ABSOLUTE_FILE));
    }
    Assert.assertEquals(2, fList.size());
  }

  @Test
  public void testNTOT() throws IOException {
    long time = System.currentTimeMillis();
    File tmpdir = new File("." + time);
    tmpdir.mkdir();
    tmpdir.deleteOnExit();
    createFile(tmpdir, "a.xml", "a.xml".getBytes(), true);
    createFile(tmpdir, "b.xml", "b.xml".getBytes(), true);
    createFile(tmpdir, "c.props", "c.props".getBytes(), true);
    Map attrs = AbstractDataImportHandlerTest.createMap(
            FileListEntityProcessor.FILE_NAME, "xml$",
            FileListEntityProcessor.BASE_DIR, tmpdir.getAbsolutePath(),
            FileListEntityProcessor.OLDER_THAN, "'NOW'");
    Context c = AbstractDataImportHandlerTest.getContext(null,
            new VariableResolverImpl(), null, 0, Collections.EMPTY_LIST, attrs);
    FileListEntityProcessor fileListEntityProcessor = new FileListEntityProcessor();
    fileListEntityProcessor.init(c);
    List<String> fList = new ArrayList<String>();
    while (true) {
      Map<String, Object> f = fileListEntityProcessor.nextRow();
      if (f == null)
        break;
      fList.add((String) f.get(FileListEntityProcessor.ABSOLUTE_FILE));
    }
    System.out.println("List of files when given OLDER_THAN -- " + fList);
    Assert.assertEquals(2, fList.size());
    attrs = AbstractDataImportHandlerTest.createMap(
            FileListEntityProcessor.FILE_NAME, "xml$",
            FileListEntityProcessor.BASE_DIR, tmpdir.getAbsolutePath(),
            FileListEntityProcessor.NEWER_THAN, "'NOW-2HOURS'");
    c = AbstractDataImportHandlerTest.getContext(null,
            new VariableResolverImpl(), null, 0, Collections.EMPTY_LIST, attrs);
    fileListEntityProcessor.init(c);
    fList.clear();
    while (true) {
      Map<String, Object> f = fileListEntityProcessor.nextRow();
      if (f == null)
        break;
      fList.add((String) f.get(FileListEntityProcessor.ABSOLUTE_FILE));
    }
    System.out.println("List of files when given NEWER_THAN -- " + fList);
    Assert.assertEquals(2, fList.size());
  }

  public static File createFile(File tmpdir, String name, byte[] content,
                                boolean changeModifiedTime) throws IOException {
    File file = new File(tmpdir.getAbsolutePath() + File.separator + name);
    file.deleteOnExit();
    FileOutputStream f = new FileOutputStream(file);
    f.write(content);
    f.close();
    // System.out.println("before "+file.lastModified());
    if (changeModifiedTime)
      file.setLastModified(System.currentTimeMillis() - 3600000);
    // System.out.println("after "+file.lastModified());
    return file;
  }
}
