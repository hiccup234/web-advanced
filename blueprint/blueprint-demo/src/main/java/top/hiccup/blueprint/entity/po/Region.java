package top.hiccup.blueprint.entity.po;

import java.util.Set;

/**
 * Created by wenhy on 2018/1/23.
 */
public class Region {

    private Long regionId;
    private String regionName;
    private Long parRegionId;
    // 向上级联
    private Region parRegion;
    // 向下级联
    private Set<Region> childrenRegions;

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public Long getParRegionId() {
        return parRegionId;
    }

    public void setParRegionId(Long parRegionId) {
        this.parRegionId = parRegionId;
    }

    public Region getParRegion() {
        return parRegion;
    }

    public void setParRegion(Region parRegion) {
        this.parRegion = parRegion;
    }

    public Set<Region> getChildrenRegions() {
        return childrenRegions;
    }

    public void setChildrenRegions(Set<Region> childrenRegion) {
        this.childrenRegions = childrenRegion;
    }

    @Override
    public String toString() {
        return "Region{" +
                "regionId=" + regionId +
                ", regionName='" + regionName + '\'' +
                ", parRegionId=" + parRegionId +
                ", childrenRegions=" + childrenRegions +
                ", parRegion=" + parRegion +
                '}';
    }
}
