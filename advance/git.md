# git仓库清洗：

	git filter-branch --force --index-filter "git rm --cached --ignore-unmatch ./src/main/java/top/hiccup/util/git-wallet-channel.txt" --prune-empty --tag-name-filter cat -- --all
	
	注意：在Windows上面的单引号要改为双引号
	
	git push origin --force --all

https://help.github.com/en/articles/removing-sensitive-data-from-a-repository