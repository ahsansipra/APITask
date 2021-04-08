package datamodel; 
import java.util.List; 
public class Promotion{
    public String promotionId;
    public int orderId;
    public List<String> promoArea;
    public String promoType;
    public boolean showPrice;
    public boolean showText;
    public LocalizedTexts localizedTexts;
    public List<Property> properties;
    public List<Image> images;
}
