package rs.etf.sab.operations;

import java.util.List;

public interface ShopOperations {
    int	createShop(String name, String cityName);
    int	getArticleCount(int articleId);
    List<Integer> getArticles(int shopId);
    int	getCity(int shopId);
    int	getDiscount(int shopId);
    int	increaseArticleCount(int articleId, int increment);
    int	setCity(int shopId, String cityName);
    int	setDiscount(int shopId, int discountPercentage);
}
