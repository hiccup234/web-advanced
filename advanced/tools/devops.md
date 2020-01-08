
## 流程&规范
0、编码规约：P3C

1、Code Review：ReviewBoard 还是 代码讲解？

2、Git Flow：AoneFlow

3、Jira流程：

4、上线流程：结合DevOps 和 Git Flow

## Git Flow 
    git flow 
    AoneFlow

## git仓库清洗：
   * git filter-branch --force --index-filter 'git rm --cached --ignore-unmatch ./src/main/java/top/hiccup/util/***.txt' --prune-empty --tag-name-filter cat -- --all
        注意：在Windows中，上面的单引号要改为双引号
	
   * git push origin --force --all

    参考：https://help.github.com/en/articles/removing-sensitive-data-from-a-repository
