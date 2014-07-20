package actions;

import bl.beans.PatientBean;
import bl.mongobus.PatientBusiness;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import vo.table.TableHeaderVo;
import vo.table.TableInitVo;

/**
 * Created by wangronghua on 14-3-3.
 */
public class PatientAction extends BaseTableAction<PatientBusiness> {

    private PatientBean patient;

    public PatientBean getPatient() {
        return patient;
    }

    public void setPatient(PatientBean patient) {
        this.patient = patient;
    }

    @Override
    public String getActionPrex() {
        return getRequest().getContextPath() + "/patient";
    }

    @Override
    public String getCustomJs() {
        return getRequest().getContextPath() + "/js/patient.js";
    }

    @Override
    public TableInitVo getTableInit() {
        TableInitVo init = new TableInitVo();
        init.getAoColumns().add(new TableHeaderVo("registerNo", "门诊号"));
        init.getAoColumns().add(new TableHeaderVo("name", "姓名").enableSearch());
        init.getAoColumns().add(new TableHeaderVo("birthDay", "出生年月"));
        init.getAoColumns().add(new TableHeaderVo("dischargeDate", "出院时间").enableSearch());
        init.getAoColumns().add(new TableHeaderVo("diagnosis", "诊断"));
        init.getAoColumns().add(new TableHeaderVo("cellphone", "电话").setHiddenColumn(true).enableSearch());
//        init.getAoColumns().add(new TableHeaderVo("sex", "性别").addSearchOptions(new String[][] { { "-1", "1", "2" }, { "----", "男", "女" } }));
//        init.getAoColumns().add(new TableHeaderVo("age", "年龄"));
        return init;
    }

    @Override
    public String save() throws Exception {
        if (StringUtils.isBlank(patient.getId())) {
            patient.set_id(new ObjectId(getSession().getAttribute("dataId").toString()));
            getBusiness().createLeaf(patient);
        } else {
            PatientBean origBean = (PatientBean) getBusiness().getLeaf(patient.getId().toString()).getResponseData();
            BeanUtils.copyProperties(patient, origBean);
            getBusiness().updateLeaf(origBean, patient);
        }
        return SUCCESS;
    }

    @Override
    public String edit() throws Exception {
        patient = (PatientBean) getBusiness().getLeaf(getId()).getResponseData();
        getSession().setAttribute("dataId", patient.getId());
        return SUCCESS;
    }

    @Override
    public String delete() throws Exception {
        if (getIds() != null) {
            for (String id : getIds()) {
                getBusiness().deleteLeaf(id);
            }
        }
        return SUCCESS;
    }

}
