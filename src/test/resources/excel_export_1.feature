Feature:  创建excel导出规则，一条导出规则包含url,请求参数，特定值，一条规则对应一个excel文件，一个文件有多个sheet页，一个sheet页包含多个row,一个row包含多个cell以及对应的excel样式，
  目前row操作比较少


  Scenario: 新增导出规则
    Given 工单539031235207680
    When database_logic_handler
    Then I should be told deviceType is  " " matrix is length is"2"


  Scenario: 为导出规则新增sheet页
    Given 工单539031235207680
    When database_logic_handler
    Then I should be told deviceType is  " " matrix is length is"2"

  Scenario: 为sheet页新增row
    Given 工单539031235207680
    When database_logic_handler
    Then I should be told deviceType is  " " matrix is length is"2"


  Scenario: 为sheet页新增cell
    Given 工单539031235207680
    When database_logic_handler
    Then I should be told deviceType is  " " matrix is length is"2"


  Scenario: 分页查询导出规则
    Given 工单539031235207680
    When database_logic_handler
    Then I should be told deviceType is  " " matrix is length is"2"


  Scenario: 获取导出规则详情，包含sheet，row,cell 等详细信息
    Given 工单539031235207680
    When database_logic_handler
    Then I should be told deviceType is  " " matrix is length is"2"



