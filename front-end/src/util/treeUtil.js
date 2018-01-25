var ids = [];
export function builderTreeIdSeq(treeList, treeId) {
    ids = [];
    var nodeObj = getNode(treeList, treeId)
    if (nodeObj.parentNode != null) {
        //ids.unshift(nodeObj.node.parentId); //push末尾添加,unshift开头添加
        unshiftParentId(nodeObj.parentNode);
    }
    return ids;
}

function unshiftParentId(pnode) {
    if(pnode){
        ids.unshift(pnode.parentId);
        if (pnode.parentNode) {
            unshiftParentId(pnode.parentNode);
        }
    }
    
}


/**
 * 根据NodeID查找当前节点以及父节点
 */
var parentNode = null;
var node = null;
function getNode(tree, nodeId) {

    //1.第一层 root 深度遍历整个tree
    for (var i = 0; i < tree.length; i++) {
        if (node) {
            break;
        }
        var obj = tree[i];
        //没有就下一个
        if (!obj || !obj.id) {
            continue;
        }

        //2.有节点就开始找，一直递归下去
        if (obj.id == nodeId) {
            //找到了与nodeId匹配的节点，结束递归
            node = obj;
            break;
        } else {
            //3.如果有子节点就开始找
            if (obj.children) {
                //4.递归前，记录当前节点，作为parent 父亲
                parentNode = obj;
                //递归往下找
                getNode(obj.children, nodeId);
            } else {
                //跳出当前递归，返回上层递归
                continue;
            }
        }
    }

    //5.如果木有找到父节点，置为null，因为没有父亲  
    if (!node) {
        parentNode = null;
    }

    //6.返回结果obj
    return {
        parentNode: parentNode,
        node: node
    };
}
