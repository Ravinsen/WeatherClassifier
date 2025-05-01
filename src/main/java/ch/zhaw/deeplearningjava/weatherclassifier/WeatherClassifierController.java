package ch.zhaw.deeplearningjava.weatherclassifier;

import ai.djl.Model;
import ai.djl.inference.Predictor;
import ai.djl.modality.Classifications;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.modality.cv.transform.Resize;
import ai.djl.modality.cv.transform.ToTensor;
import ai.djl.modality.cv.translator.ImageClassificationTranslator;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.translate.TranslateException;
import ai.djl.translate.Translator;

import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class WeatherClassifierController {

    private Predictor<Image, Classifications> predictor;

    private static final Map<String, String> EMOJI_MAP = new HashMap<>();
    static {
        EMOJI_MAP.put("sunny", "☀️");
        EMOJI_MAP.put("rainy", "🌧️");
        EMOJI_MAP.put("snowy", "❄️");
        EMOJI_MAP.put("cloudy", "☁️");
        EMOJI_MAP.put("hail", "🌨️");
        EMOJI_MAP.put("lightning", "⚡");
        EMOJI_MAP.put("rainbow", "🌈");
        EMOJI_MAP.put("sunrise", "🌅");
    }

    @PostConstruct
    public void init() throws Exception {
        List<String> labels = Files.readAllLines(Paths.get("src/main/resources/models/synset.txt"));

        Translator<Image, Classifications> translator = ImageClassificationTranslator.builder()
                .addTransform(new Resize(224, 224))
                .addTransform(new ToTensor())
                .optApplySoftmax(true)
                .optSynset(labels)
                .build();

                Criteria<Image, Classifications> criteria = Criteria.builder()
                .setTypes(Image.class, Classifications.class)
                .optModelPath(Paths.get("src/main/resources/models/weather_classifier.onnx"))
                .optTranslator(translator)
                .optEngine("OnnxRuntime")
                .optProgress(new ai.djl.training.util.ProgressBar()) // optional, für Feedback
                .build();        

        ZooModel<Image, Classifications> model = criteria.loadModel();
        this.predictor = model.newPredictor();
    }

    @PostMapping(value = "/analyze", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, Object> analyze(@RequestParam("image") MultipartFile file) {
        Map<String, Object> response = new HashMap<>();
        try {
            BufferedImage bi = ImageIO.read(file.getInputStream());
            Image img = ImageFactory.getInstance().fromImage(bi);
            Classifications result = predictor.predict(img);

            Classifications.Classification best = result.best();
            String className = best.getClassName().toLowerCase();
            String emoji = EMOJI_MAP.getOrDefault(className, "❓");

            response.put("wetter", emoji + " " + capitalize(className));
            response.put("confidence", String.format("%.2f", best.getProbability()));
        } catch (TranslateException | IOException e) {
            response.put("error", e.getMessage());
        }
        return response;
    }

    private String capitalize(String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }
}
