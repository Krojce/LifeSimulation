package manager;

import font.FontType;
import font.GUIText;
import font.TextMeshData;
import loader.Loader;
import render.FontRenderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextManager {

    private static Loader loader;
    private static Map<FontType, List<GUIText>> texts = new HashMap<FontType, List<GUIText>>();
    private static FontRenderer renderer;

    public TextManager(Loader loader) {
        TextManager.loader = loader;
        renderer = new FontRenderer();
    }

    public static void loadText(GUIText text) {
        FontType font = text.getFont();
        TextMeshData data = font.loadText(text);
        int vao = loader.loadToVAO(data.getVertexPositions(), data.getTextureCoords());
        text.setMeshInfo(vao, data.getVertexCount());
        List<GUIText> textBatch = texts.get(font);
        if (textBatch == null) {
            textBatch = new ArrayList<GUIText>();
            texts.put(font, textBatch);
        }
        textBatch.add(text);
    }

    public static void removeText(GUIText text) {
        List<GUIText> textBatch = texts.get(text.getFont());
        textBatch.remove(text);
        if (textBatch.isEmpty()) {
            texts.remove(texts.get(text.getFont()));
        }
    }

    public void render() {
        renderer.render(texts);
    }

    public void cleanUp() {
        renderer.cleanUp();
    }

}
