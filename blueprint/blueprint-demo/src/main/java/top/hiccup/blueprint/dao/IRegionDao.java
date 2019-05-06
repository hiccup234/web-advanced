package top.hiccup.blueprint.dao;


import top.hiccup.blueprint.entity.po.Region;

import java.io.IOException;
import java.util.List;

/**
 * Created by wenhy on 2018/1/23.
 */
public interface IRegionDao {

    List<Region> queryChildrenRegionsByPar(Long regionId) throws IOException;

    List<Region> queryChildrenRegionsAndSelfByPar(Long regionId) throws IOException;

    List<Region> queryRegionAndParById(Long regionId) throws IOException;

}
