package uengine.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TexturedModel {

    private RawModel rawModel;
    private Texture texture;
}
