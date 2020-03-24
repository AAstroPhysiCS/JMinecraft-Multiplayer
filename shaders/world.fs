#version 460 core

layout (location = 0) out vec4 color;

in vec2 texCoordsOut;
in float brightnessOut;

uniform sampler2D tex;
uniform int selected;

void main(){
    color = texture(tex, texCoordsOut);

    if(color.xyzw == vec4(1.0f, 1.0f, 1.0f, 1.0f))
            discard;
    color *= brightnessOut;
    if(selected == 1){
        color.rgba = vec4(1.0f, 0.0f, 0.0f, 1.0f);
    }
}