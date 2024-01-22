import kotlinx.serialization.*
import com.google.android.gms.maps.model.LatLng
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializer(forClass = LatLng::class)
object LatLngSerializer : KSerializer<LatLng> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("LatLng", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: LatLng) {
        encoder.encodeString("${value.latitude},${value.longitude}")
    }

    override fun deserialize(decoder: Decoder): LatLng {
        val parts = decoder.decodeString().split(",")
        return LatLng(parts[0].toDouble(), parts[1].toDouble())
    }
}
