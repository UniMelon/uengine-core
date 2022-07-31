#version 400 core

// in vec3 color; // входные данные - это выходные из vertexShader
in vec2 pass_texCoords;
out vec4 out_Color;

uniform sampler2D texSampler;

void main() {
//     out_Color = vec4(color, 1.0); // 1.0-alpha, преобр вх цвета в выходной
    out_Color = texture(texSampler, pass_texCoords);
}