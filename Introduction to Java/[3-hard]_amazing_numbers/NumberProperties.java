package numbers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NumberProperties {
    final private long number;
    private final Map<Property, Boolean> properties;


    public NumberProperties(long number) {
        this.number = number;
        this.properties = new HashMap<>();

        for (Property property : Property.values()) {
            properties.put(property, property.test(number));
        }
    }
    public boolean hasProperty(Property property) {
        return properties.get(property);
    }

    public String oneNumberString() {
        StringBuilder sb = new StringBuilder();
        for (Property property : Property.values()) {
            sb.append(String.format("%s: %b\n", property.name().toLowerCase(), hasProperty(property)));
        }
        return sb.toString();
    }


    public String multiNumberString() {
        List<String> listOfProperties = new ArrayList<>();
        for (Property property : Property.values()) {
            if (hasProperty(property)) {
                listOfProperties.add(property.name().toLowerCase());
            }
        }
        return number + " is " + String.join(", ", listOfProperties);
    }

}
