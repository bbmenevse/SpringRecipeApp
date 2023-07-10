package guru.springframework.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import guru.springframework.domain.UnitOfMeasure;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class UnitOfMeasureJsonDeserializer extends JsonDeserializer<UnitOfMeasure> {
    @Override
    public UnitOfMeasure deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String description = jsonParser.getText();
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setDescription(description);
        return unitOfMeasure;
    }
}