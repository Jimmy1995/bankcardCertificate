var curMenu = null, zTree_Menu = null;
		var setting = {
			view: {
				showLine: true,
				selectedMulti: false,
				dblClickExpand: false
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onNodeCreated: this.onNodeCreated,
				beforeClick: this.beforeClick,
				onClick: doSomeThing,
				onRightClick: this.onRightClick
			}
		};
 
		
		function beforeClick(treeId, node) {
			if (node.isParent) {
				if (node.level === 0) {
					var pNode = curMenu;
					while (pNode && pNode.level !==0) {
						pNode = pNode.getParentNode();
					}
					if (pNode !== node) {
						var a = $("#" + pNode.tId + "_a");
						a.removeClass("cur");
						zTree_Menu.expandNode(pNode, false);
					}
					a = $("#" + node.tId + "_a");
					a.addClass("cur");
 
					var isOpen = false;
					for (var i=0,l=node.children.length; i<l; i++) {
						if(node.children[i].open) {
							isOpen = true;
							break;
						}
					}
					if (isOpen) {
						zTree_Menu.expandNode(node, true);
						curMenu = node;
					} else {
						zTree_Menu.expandNode(node.children[0].isParent?node.children[0]:node, true);
						curMenu = node.children[0];
					}
				} else {
					zTree_Menu.expandNode(node);
				}
			}
			return !node.isParent;
		}
		
		
		
		function selectMenu(menuId,zTreeObj){//选中菜单展开
		    	var zTree =zTreeObj //$.fn.zTree.getZTreeObj("treeDemo");
		    	var node = zTree.getNodeByParam("id", menuId, null);
		    	if(node){
		    		if(node.isParent){
						zTree.expandNode(node, true, false, true, false);	
					} else {
						zTree.expandNode(node.getParentNode(), true, false, true, false);
					}
		    		zTree.selectNode(node, false);
		    	}
		 }