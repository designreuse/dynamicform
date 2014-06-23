package bl.beans;

import org.mongodb.morphia.annotations.Entity;

/**
 * Created by wangronghua on 14-6-17.
 */
@Entity(value = "document")
public class DocumentBean extends Bean{

  private String tableName;
  private String englishName;
  private int type; //0:包含有子元素；1:包含无子元素
  private String code; //代码
  private String abbreviation; //缩写
  private int columnCount;
  private boolean displayNameOrNot;
  private int standardCategory; //标准分类: 0:CDISC, 1:机构标准, 2:非标准
  private String description;

  public String getAbbreviation() {
    return abbreviation;
  }

  public void setAbbreviation(String abbreviation) {
    this.abbreviation = abbreviation;
  }

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getEnglishName() {
    return englishName;
  }

  public void setEnglishName(String englishName) {
    this.englishName = englishName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getColumnCount() {
    return columnCount;
  }

  public void setColumnCount(int columnCount) {
    this.columnCount = columnCount;
  }

  public boolean isDisplayNameOrNot() {
    return displayNameOrNot;
  }

  public void setDisplayNameOrNot(boolean displayNameOrNot) {
    this.displayNameOrNot = displayNameOrNot;
  }

  public int getStandardCategory() {
    return standardCategory;
  }

  public void setStandardCategory(int standardCategory) {
    this.standardCategory = standardCategory;
  }

}