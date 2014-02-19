package actions;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;

import bl.beans.PageBean;
import bl.beans.TemplateBean;
import bl.common.BusinessResult;
import bl.constants.BusTieConstant;
import bl.instancepool.SingleBusinessPoolManager;
import bl.mongobus.FormBusiness;
import bl.mongobus.PageBusiness;

public class PageAction extends ActionSupport {
    public static final String PATIENTNAME = "patient";
    public static final String REVISITNAME = "revisit";
    private List<String[]> templateList = new ArrayList<String[]>();
    private String[] patientCheckbox = new String[] {};

    private final Map<String, String> pageMap = new LinkedHashMap<String, String>() {
        {
            put(PATIENTNAME, "病人管理配置");
            put(REVISITNAME, "随访管理配置");
        }
    };

    public Map<String, String> getPageMap() {
        return pageMap;
    }

    /**
     * @return the templateList
     */
    public List<String[]> getTemplateList() {
        return templateList;
    }

    /**
     * @param templateList
     *            the templateList to set
     */
    public void setTemplateList(List<String[]> templateList) {
        this.templateList = templateList;
    }

    public String[] getPatientCheckbox() {
        return patientCheckbox;
    }

    public void setPatientCheckbox(String[] patientCheckbox) {
        this.patientCheckbox = patientCheckbox;
    }

    public String pageSave() {
        try {
            PageBusiness pab = (PageBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_PAGEBUSINESS);
            FormBusiness fb = (FormBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_FORMBUSINESS);

            Object record = pab.getLeafByName(this.PATIENTNAME).getResponseData();
            List<TemplateBean> tbs = new ArrayList<TemplateBean>();
            if (this.patientCheckbox != null) {
                for (int i = 0; i < this.patientCheckbox.length; i++) {
                    TemplateBean tb = (TemplateBean) fb.getLeafByName(this.patientCheckbox[i]).getResponseData();
                    tbs.add(tb);
                }
            }
            // update patient info in page table.
            if (record != null) {
                PageBean orginalBean = (PageBean) record;
                PageBean newBean = (PageBean) orginalBean.clone();
                newBean.setTemplateList(tbs);
                pab.updateLeaf(orginalBean, newBean);
            } else {
                PageBean pbean = new PageBean();
                pbean.setName(this.PATIENTNAME);
                pbean.setTemplateList(tbs);
                pbean.setLabel("病人管理页面");
                pab.createLeaf(pbean);
            }

        } catch (Exception e) {
            LOG.error("this exception [#0]", e.getMessage());
        }
        return ActionSupport.SUCCESS;
    }

    public String pageEdit() {
        try {

            FormBusiness fb = (FormBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_FORMBUSINESS);
            // find all records in the template;
            BusinessResult br = fb.getAllLeaves();
            List<TemplateBean> elements = (List<TemplateBean>) br.getResponseData();
            for (TemplateBean te : elements) {
                String[] row = new String[] { te.getLabel(), te.getName() };
                this.templateList.add(row);
            }

            PageBusiness pab = (PageBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_PAGEBUSINESS);
            Object record = pab.getLeafByName(this.PATIENTNAME).getResponseData();
            if (record != null) {
                List<TemplateBean> listRecord = ((PageBean) record).getTemplateList();
                String[] createString = new String[listRecord.size()];
                for (int i = 0; i < listRecord.size(); i++) {
                    createString[i] = listRecord.get(i).getName();
                }
                this.patientCheckbox = createString;
            }
        } catch (Exception e) {
            LOG.error("this exception [#0]", e.getMessage());
        }
        return ActionSupport.SUCCESS;
    }

    public static void main(String[] args) {
        FormBusiness fb = (FormBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_FORMBUSINESS);
        // create a leaf in the MongoDB.
        TemplateBean tb = new TemplateBean();
        tb.setLabel("测试数据1");
        tb.setName("template1");
        tb.setPath("xml/template1.xml");
        fb.createLeaf(tb);

        PageBusiness pa = (PageBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_PAGEBUSINESS);
        // create a page in the MongoDB.
        PageBean pab = new PageBean();
        pab.setLabel("病人管理");
        pab.setName("page1");
        List<TemplateBean> list = new ArrayList<TemplateBean>();
        list.add(tb);
        pab.setTemplateList(list);
        pa.createLeaf(pab);

        PageBean getP = (PageBean) pa.getLeafByName("page1").getResponseData();
        System.out.println(getP.getTemplateList().get(0).getPath());

        // delete this record.

        pa.deleteLeaf(getP.get_id().toString());

        fb.deleteLeaf(getP.getTemplateList().get(0).get_id().toString());
    }
}
