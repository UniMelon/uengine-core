#version 400 core

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 texCoords;
// out vec3 color;
out vec2 pass_texCoords;

uniform mat4 transformationMatrix;

void main() {
    gl_Position = vec4(position, 1.0);
//     color = vec3(position.x+0.7, 1.0, position.y+0.7);
    pass_texCoords = texCoords;
}
