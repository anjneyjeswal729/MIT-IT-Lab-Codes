import numpy as np


# Function to compute the Euclidean distance
def euclidean_distance(a, b):
    return np.sqrt(np.sum((a - b) ** 2))


# Function to assign data points to the nearest centroid
def assign_clusters(data, centroids):
    clusters = {}
    for i in range(len(centroids)):
        clusters[i] = []

    for point in data:
        distances = [euclidean_distance(point, centroid) for centroid in centroids]
        nearest_centroid = np.argmin(distances)
        clusters[nearest_centroid].append(point)

    return clusters


# Function to update the centroids
def update_centroids(clusters, data):
    new_centroids = []
    for cluster in clusters.values():
        if len(cluster) > 0:
            new_centroids.append(np.mean(cluster, axis=0))
        else:
            new_centroids.append(data[np.random.choice(range(len(data)))])

    return np.array(new_centroids)


# K-means clustering algorithm
def kmeans(data, k, max_iters=100, tol=1e-4):
    # Randomly initialize centroids from the data points
    centroids = data[np.random.choice(range(len(data)), size=k, replace=False)]


    for i in range(max_iters):
        # Step 1: Assign clusters based on closest centroid
        clusters = assign_clusters(data, centroids)

        # Step 2: Update centroids
        new_centroids = update_centroids(clusters, data)

        # Check if centroids have converged (using a tolerance threshold)
        if np.all(np.abs(new_centroids - centroids) < tol):
            print(f"Converged at iteration {i}")
            break

        centroids = new_centroids

    return centroids, clusters


# Example usage
if __name__ == "__main__":
    # Example dataset (2D points)
    data = np.array([[1, 2], [1, 3], [2, 1], [6, 5], [7, 6], [8, 5]])

    # Number of clusters (k)
    k = 2

    centroids, clusters = kmeans(data, k)

    print("Centroids:", centroids)
    print("Clusters:", clusters)