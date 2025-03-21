import numpy as np

def euclidean_distance(a, b):
    """Compute the Euclidean distance between two points a and b."""
    return np.sqrt(np.sum((a - b) ** 2))

def kmeans(X, K, max_iters=100, tol=1e-4):
    """
    K-means algorithm implementation.
    
    Parameters:
    - X: Dataset (numpy array of shape (n_samples, n_features))
    - K: Number of clusters
    - max_iters: Maximum number of iterations
    - tol: Tolerance for convergence (change in centroids)

    Returns:
    - centroids: Final centroids (numpy array of shape (K, n_features))
    - labels: Cluster labels for each data point (numpy array of shape (n_samples,))
    """
    # Step 1: Initialize centroids randomly from the data points
    n_samples, n_features = X.shape
    centroids = X[np.random.choice(n_samples, K, replace=False)]
    
    for iteration in range(max_iters):
        # Step 2: Assign each point to the nearest centroid
        labels = np.array([np.argmin([euclidean_distance(x, c) for c in centroids]) for x in X])

        # Step 3: Update centroids
        new_centroids = np.array([X[labels == i].mean(axis=0) for i in range(K)])

        # Step 4: Check for convergence (if centroids do not change)
        if np.all(np.abs(new_centroids - centroids) < tol):
            print(f"Convergence reached after {iteration + 1} iterations")
            break
        
        centroids = new_centroids

    return centroids, labels

# Example usage:
if __name__ == "__main__":
    # Sample dataset (2D)
    X = np.array([
        [1.0, 2.0],
        [1.5, 1.8],
        [5.0, 8.0],
        [8.0, 8.0],
        [1.0, 0.6],
        [9.0, 11.0],
        [8.0, 2.0],
        [10.0, 2.0],
        [9.0, 3.0]
    ])
    
    # Run K-means with K=3 clusters
    K = 3
    centroids, labels = kmeans(X, K)
    
    print("Centroids:\n", centroids)
    print("Labels:\n", labels)
