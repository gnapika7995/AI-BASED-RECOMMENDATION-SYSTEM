import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class RecommenderApp {
    public static void main(String[] args) {
        try {
            // Load data from CSV file
            DataModel model = new FileDataModel(new File("data.csv"));

            // Define user similarity metric
            UserSimilarity similarity = new PearsonCorrelationSimilarity(model);

            // Define neighborhood (top 2 similar users)
            UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);

            // Build the recommender
            Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

            // Recommend top 3 items for user 1
            List<RecommendedItem> recommendations = recommender.recommend(1, 3);

            // Output recommendations
            System.out.println("Recommendations for User 1:");
            for (RecommendedItem recommendation : recommendations) {
                System.out.printf("Item ID: %d, Estimated Rating: %.2f%n",
                        recommendation.getItemID(), recommendation.getValue());
            }

        } catch (IOException | TasteException e) {
            e.printStackTrace();
        }
    }
}
