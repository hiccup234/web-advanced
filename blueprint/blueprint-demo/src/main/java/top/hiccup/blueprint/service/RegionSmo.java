package top.hiccup.blueprint.service;


import top.hiccup.blueprint.dao.IRegionDao;
import top.hiccup.blueprint.entity.po.Region;

import java.io.IOException;
import java.util.List;

/**
 * Created by wenhy on 2018/1/23.
 */
public class RegionSmo {

    private IRegionDao regionDao;


    public void testDao() throws IOException {

//        // 自关联查询（级联）（一对多实现：向下级联）
//        regionDao = SessionUtils.getSession().getMapper(IRegionDao.class);
//        List<Region> regions = regionDao.queryChildrenRegionsByPar(10000L);
//        for(Region region : regions) {
//            System.out.println(region);
//        }

//        // 自关联查询（级联）（一对多实现：向下级联，包括指定参数本身）
//        regionDao = SessionUtils.getSession().getMapper(IRegionDao.class);
//        List<Region> regions = regionDao.queryChildrenRegionsAndSelfByPar(10000L);
//        for(Region region : regions) {
//            System.out.println(region);
//        }

        // 自关联查询（级联）（一对多实现：向上级联，包括指定参数本身）
        regionDao = SessionUtils.getSession().getMapper(IRegionDao.class);
        List<Region> regions = regionDao.queryRegionAndParById(11003L);
        for(Region region : regions) {
            System.out.println(region);
        }


    }

    public static void main(String[] args) {
        RegionSmo regionSmo = new RegionSmo();
        try {
            regionSmo.testDao();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
