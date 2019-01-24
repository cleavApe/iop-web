package com.sitech.billing.datainput.controller;

import com.sitech.billing.common.bean.JsonResult;
import com.sitech.billing.customization.table.model.Table;
import com.sitech.billing.datainput.fileparse.BaseFileParse;
import com.sitech.billing.datainput.fileparse.FileParseFactory;
import com.sitech.billing.datainput.model.DataInputTable;
import com.sitech.billing.system.base.BaseController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.InputStream;
import java.util.List;

/**
 * 数据导入
 *
 * @author sunzhen
 * @date 2019/1/23 11:26
 */
@RestController
@RequestMapping("/input")
public class DataInputController extends BaseController {

    @RequestMapping("/page")
    public ModelAndView page() {
        return new ModelAndView("datainput/datainput");
    }

    @PostMapping("/upload")
    public JsonResult upload(@RequestParam("file") MultipartFile file,
                             @RequestParam("inputId") int id,
                             @RequestParam("fileType") int fileType) {
        if (file.isEmpty()) {
            throw new RuntimeException("上传失败，请选择文件");
        }
        try {
            InputStream is = file.getInputStream();
            BaseFileParse fileParse = FileParseFactory.getFileParse(fileType);
            List<String> list = fileParse.parse(is);
            DataInputTable table = getTable();
            int rows = dataInputService.batchSave(list, table);
            return JsonResult.success(rows);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //测试
    public DataInputTable getTable() {
        DataInputTable table = new DataInputTable();
        table.setInputTableId(1);
        table.setTableDesc("用户信息插入");
        table.setTableName("iop_test_table");
        table.setTableFields("test_id,test_name,test_code,test_date,test_order,test_linkage");
        table.setUserId(1);
        return table;
    }
}
