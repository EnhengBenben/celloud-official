package com.nova.dao;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;
import com.nova.dao.impl.ProjectDaoImpl;
import com.nova.sdo.Data;
import com.nova.sdo.Project;

/**
 * 项目管理接口
 * 
 * @author <a href="mailto:liuqingxiao@novacloud.com">liuqx</a>
 * @date 2013-5-20下午02:57:51
 * @version Revision: 1.0
 */
@ImplementedBy(ProjectDaoImpl.class)
public interface IProjectDao {

    /**
     * 判断新增项目是否成功
     * 
     * @param project
     *            项目对象
     * @return
     */
    boolean insertProject(Project project);

    /**
     * 创建项目并返回projectId
     * 
     * @param project
     * @return
     */
    int createProject(Project project);

    /**
     * 获取指定项目下的所有文件信息
     * 
     * @param projectId
     * @return
     */
    List<Data> getAllFileInProject(int projectId);

    /**
     * 获取用户所有项目名称及编号-单页展示
     * 
     * @param userId
     * @return
     */
    List<Project> getAllProNameList(int userId);

    /**
     * 根据文件编号获取项目列表
     * 
     * @param userId
     * @return
     */
    List<Project> getAllProNameListByFileId(int fileId);

    /**
     * 新增共享项目
     * 
     * @param userId
     * @param projectId
     * @return
     */
    int insertShareProject(String userIds, int projectId, int userId);

    /**
     * 根据项目编号查询项目名称
     * 
     * @param projectId
     * @return
     */
    public String getProjectNameById(int projectId);

    /**
     * 根据项目名称查询项目编号
     * 
     * @param projectName
     * @return
     */
    public int getProjectIdByName(String projectName);

    /**
     * 获取数据库中的所有项目名称
     * 
     * @return
     */
    public List<String> getAllProName();

    /**
     * 给指定的项目添加物种信息
     * 
     * @param projectId
     * @param strain
     * @return
     */
    public boolean addProStrain(int projectId, String strain);

    /**
     * 给指定的项目添加数据类型信息
     * 
     * @param projectId
     * @param dataType
     * @return
     */
    public boolean addProDataType(int projectId, String dataType);

    /**
     * 项目移除数据
     * 
     * @param projectId
     * @param dataId
     * @return
     */
    public boolean removeData(String projectId, int dataId);

    /**
     * 给项目添加数据
     * 
     * @param projectId
     * @param dataId
     * @return
     */
    int addDataToPro(int projectId, int dataId);

    /**
     * 根据项目编号查询物种信息
     * 
     * @param projectId
     * @return
     */
    public String getStrainByProId(int projectId);

    /**
     * 获取用户输入过的项目物种信息
     * 
     * @param userId
     * @return
     */
    public List<Map<String, String>> getProStrainItem(int userId);

    /**
     * 根据项目编号获取用户编号
     * 
     * @param projectId
     * @return
     */
    public int getUserIdByProjectId(int projectId);

    /**
     * 更改项目名称
     * 
     * @param projectId
     * @return
     */
    boolean updateProName(int projectId, String projectName);

    /**
     * 为数据分配项目时查询符合数据添加条件的项目列表，包括没有文件格式的项目
     * 
     * @return
     */
    List<Project> getProListForData(int userId, int fileType);

    /**
     * 根据共享列表编号删除共享记录
     * 
     * @return
     */
    boolean deleteSharedProById(int sharedId);

    /**
     * 删除已共享的项目
     * 
     * @param userId
     * @param projectId
     * @return
     */
    boolean deleteSharedPro(int userId, int projectId);

    /**
     * 查询所有已被取消共享的用户所再次共享的记录编号
     * 
     * @param userId
     * @param projectId
     * @return
     */
    Map<String, Integer> getAllErrorSharedPro(int userId, int projectId);

    /**
     * 根据项目编号获取项目数据类型
     * 
     * @param projectId
     * @return
     */
    String getDataTypeById(int projectId);

    /**
     * 检查用户下项目名是否重复
     * 
     * @param projectName
     * @param userId
     * @return 返回项目编号
     */
    public int checkProjectName(int projectId, String projectName, int userId);

    /**
     * 修改项目文件格式
     * 
     * @param projectId
     * @param format
     * @return
     */
    public int updateDataFormatById(int projectId, int format);

    /**
     * 项目删除
     * 
     * @param projectId
     * @return
     */
    public int deleteProject(int projectId);

    /**
     * 根据项目编号获取项目信息
     * 
     * @param projectId
     * @return
     */
    public Project getProjectById(int projectId);

    /**
     * 获取数据的所属的项目编号
     * 
     * @param fileId
     * @return
     */
    public List<Integer> getProIdsByFileId(int fileId);
}
