package cn.pa.jsoup.Manager;

import cn.pa.jsoup.Utils.DownloadFileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.utils.FilePersistentBase;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 文都
 */
public class WDPipeline extends FilePersistentBase implements Pipeline {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private String path = "D:/page/wd";
    public WDPipeline() {
        setPath(path);
    }

    public WDPipeline(String path) {
        setPath(path);
    }
    @Override
    public void process(ResultItems resultItems, Task task) {
        Map<String,Object> result =  resultItems.getAll();
        String fileName;
        for(Map.Entry<String, Object> entry : resultItems.getAll().entrySet()){
            fileName= entry.getKey().substring(entry.getKey().lastIndexOf("/")+1);
            try {
                PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(
                        getFile(fileName)),"utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if(entry.getKey()!=null){
                    try {
//                        DownloadFileUtils.downloadFile(path,fileName,entry.getValue().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        }
    }
}
