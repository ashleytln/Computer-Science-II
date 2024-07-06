public class BinTree<G extends Comparable<G>> {
   // Member variable
   Node<G> root;
   
   // Default constructor
   public <G> BinTree() {
      root = null;
   }
   
   // Recursive insert method with private helper
   public void insert(G g) {
		root = insert(root, g);
	}
	// Returns root of updated BST to public insert method
	private Node<G> insert(Node<G> node, G g) {
		// If node (root) does not exist, return new Node with payload g to be inserted as root
		if (node == null)
			return new Node<G>(g);
		// If g is less than current node's payload, traverse to current node's left node
		// Current left node is set to whatever is returned from insert method
		else if (g.compareTo(node.getPayload()) < 0)
			node.left = insert(node.left, g);
		// If g is greater than current node's payload, traverse to current node's right node
		// Current right node is set to whatever is returned from insert method
		else if (g.compareTo(node.getPayload()) > 0)
			node.right = insert(node.right, g);
		// Return root of updated BST
		return node;
	}
   
   // Recursive search with private helper, returns payload (null if does not exist in tree)
   public G search(G g) {
      return search(root, g);
   }
   // Search helper function, returns payload if found or null if not found
   private G search(Node<G> node, G g) {
		// If node is null, BST is empty or value cannot be found in traversal, quit traversal
		if (node == null)
		   return null;
		// If g is less than current node's payload, search current node's left subtree
		else if (g.compareTo(node.getPayload()) < 0)
   		return search(node.left, g);
		// If g is greater than current node's payload, search current node's right subtree
		else if (g.compareTo(node.getPayload()) > 0)
			return search(node.right, g);
		// If g is equal to current node's payload, search successful, return payload
		else
			return node.getPayload();
	}
   
   // Recursive delete with private helper
   public void delete(G g) {
	   // BST after Node removal
		root = delete(root, g);
	}
	// Returns root of updated BST to public delete method
	private Node<G> delete(Node<G> node, G g) {
		Node<G> parent = null;
		// If node (or root) is null, no more traversal needed (or nothing to delete)
		if (node == null)
		   return null;
		// If g is less than current node's payload, traverse to current node's left node
		// Current left node is set to whatever is returned by delete method
		else if (g.compareTo(node.getPayload()) < 0) {
		   node.left = delete(node.left, g);
		}
		// If g is greater than current node's payload, traverse to current node's right node
		// Current right node is set to whatever is returned by delete method
		else if (g.compareTo(node.getPayload()) > 0) {
		   node.right = delete(node.right, g);
		}
		// If g is equal to current node's payload, node to be deleted has been found
		else {
			// If node has 0 children, return null
			if (node.left == null && node.right == null) {
			   // If node is root
			   if (parent == null)
			      return null;
			   else if (node.compareTo(parent.left) == 0)
			      parent.left = null;
			   else
			      parent.right = null;
			}
			// If node has left child and no right child, return left child
			else if (node.right == null) {
			   if (parent == null)
			      return node.left;
			   else if (node.compareTo(parent.left) == 0)
			      parent.left = node.left;
			   else
			      parent.right = node.left;
			}
			// If the node has right child and no left child, return right child
			else if (node.left == null) {
			   if (parent == null)
			      return node.right;
			   else if (node.compareTo(parent.left) == 0)
			      parent.left = node.right;
			   else
			      parent.right = node.right;
			}
			// If node (parent) has two children
			else {
			   // Start traversal in parent node's right subtree
			   Node<G> ptr = root.right;
			   // Traverse to bottom left of right subtree (find successor)
			   while (ptr.left != null)
			      ptr = ptr.left;
			   // Store parent's payload in temp variable
			   G temp = node.getPayload();
            // Set parent node's payload to successor's payload
			   node.setPayload(ptr.getPayload());
				// Set parent node's right child to whatever is returned when deleting parent's original payload
				node.right = delete(node.right, temp);
			}
		}
		// Returns node, final return is root of updated BST
		return node;
	}
}
