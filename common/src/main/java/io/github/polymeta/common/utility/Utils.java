package io.github.polymeta.common.utility;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.List;
import java.util.stream.Collectors;

public class Utils
{
    public static List<Text> deserializeLore(List<String> _itemLoreString)
    {
        return _itemLoreString.stream()
                .map(TextSerializers.FORMATTING_CODE::deserialize)
                .collect(Collectors.toList());
    }
}
