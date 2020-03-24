#version 460 core

layout(location = 0) in vec4 pos;
layout(location = 1) in vec2 tcs;
layout(location = 3) in float brightness;
layout(location = 2) uniform mat4 vw_matrix;

out vec2 texCoordsOut;
out float brightnessOut;

void main(){
    gl_Position = pos * vw_matrix;
    texCoordsOut = tcs;
    brightnessOut = brightness;
}