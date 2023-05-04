package Seminar4;

public class RedBlackTree {

    private Node root;

    /**
     * Метод добавления узла красно-черного дерева
     *
     * @param node  ссылка на родительский узел
     * @param value значение узла
     * @return true or false
     */
    private boolean addNode(Node node, int value) {
        if (node.value == value) {
            return false;
        } else {
            if (node.value > value) {
                if (node.leftChild != null) {
                    boolean result = addNode(node.leftChild, value);
                    node.leftChild = rebalance(node.leftChild);
                    return result;
                } else {
                    node.leftChild = new Node();
                    node.leftChild.color = Color.RED;
                    node.leftChild.value = value;
                    return true;
                }
            } else {
                if (node.rightChild != null) {
                    boolean result = addNode(node.rightChild, value);
                    node.rightChild = rebalance(node.rightChild);
                    return result;
                } else {
                    node.leftChild = new Node();
                    node.rightChild.color = Color.RED;
                    node.rightChild.value = value;
                    return true;
                }
            }
        }
    }

    public boolean add(int value) {
        if (root != null) {
            boolean result = addNode(root, value);
            root = rebalance(root);
            root.color = Color.BlACK;
            return result;
        } else {
            root = new Node();
            root.color = Color.BlACK;
            root.value = value;
            return true;
        }
    }

    /**
     * Метод ребалансировки
     *
     * @param node текущий узел
     * @return узел
     */
    private Node rebalance(Node node) {
        Node result = node;
        boolean needToRebalance;
        do {
            needToRebalance = false;
            if (result.rightChild != null && result.rightChild.color == Color.RED &&
                    (result.leftChild == null || result.leftChild.color == Color.BlACK)) {
                needToRebalance = true;
                result = rightSwap(result);
            }
            if (result.leftChild != null && result.leftChild.color == Color.RED &&
                    result.leftChild.leftChild != null && result.leftChild.leftChild.color == Color.RED) {
                needToRebalance = true;
                result = leftSwap(result);
            }
            if (result.leftChild != null && result.leftChild.color == Color.RED &&
                    result.rightChild != null && result.rightChild.color == Color.RED) {
                needToRebalance = true;
                colorSwap(result);
            }
        } while (needToRebalance);
        return result;
    }

    /**
     * Метод смены цвета
     *
     * @param node ссылка на узел-родитель
     */
    private void colorSwap(Node node) {
        node.rightChild.color = Color.BlACK;
        node.leftChild.color = Color.BlACK;
        node.color = Color.RED;
    }

    /**
     * Метод левого поворота
     *
     * @param node текущий узел
     * @return узел
     */
    private Node leftSwap(Node node) {
        Node leftChild = node.leftChild;
        Node betweenChild = leftChild.rightChild;
        leftChild.rightChild = node;
        node.leftChild = betweenChild;
        leftChild.color = node.color;
        node.color = Color.RED;
        return leftChild;
    }

    /**
     * Метод правого поворота
     *
     * @param node текущий узел
     * @return узел
     */
    private Node rightSwap(Node node) {
        Node rightChild = node.rightChild;
        Node betweenChild = rightChild.leftChild;
        rightChild.leftChild = node;
        node.rightChild = betweenChild;
        rightChild.color = node.color;
        node.color = Color.RED;
        return rightChild;
    }

    public class Node {
        private int value;
        private Color color;
        private Node leftChild;
        private Node rightChild;
    }

    private enum Color {
        RED, BlACK
    }
}
