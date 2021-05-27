import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.ptr.DoubleByReference;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;

public class LaszipApi {
    @Structure.FieldOrder({"key_id", "tiff_tag_location", "count", "value_offset"})
    public static class LaszipGeokey extends Structure {
        public int key_id;
        public int tiff_tag_location;
        public int count;
        public int value_offset;
    }

    @Structure.FieldOrder({"reserved", "user_id", "record_id", "record_length_after_header", "description", "data"})
    public static class LaszipVLR extends Structure {
        public int reserved;
        public byte[] user_id = new byte[16];
        public int record_id;
        public int record_length_after_header;
        public byte[] description = new byte[32];
        public IntByReference data;
    }

    @Structure.FieldOrder({
            "file_source_ID",
            "global_encoding",
            "project_ID_GUID_data_1",
            "project_ID_GUID_data_2",
            "project_ID_GUID_data_3",
            "project_ID_GUID_data_4",
            "version_major",
            "version_minor",
            "system_identifier",
            "generating_software",
            "file_creation_day",
            "file_creation_year",
            "header_size",
            "offset_to_point_data",
            "number_of_variable_length_records",
            "point_data_format",
            "point_data_record_length",
            "number_of_point_records",
            "number_of_points_by_return",
            "x_scale_factor",
            "y_scale_factor",
            "z_scale_factor",
            "x_offset",
            "y_offset",
            "z_offset",
            "max_x",
            "max_y",
            "max_z",
            "start_of_waveform_data_packet_record",
            "start_of_first_extended_variable_length_record",
            "number_of_extended_variable_length_records",
            "extended_number_of_point_records",
            "extended_number_of_points_by_return",
            "user_data_in_header_size",
            "user_data_in_header",

            "vlrs",
            "user_data_after_header_size",
            "user_data_after_header"
    })
    public static class LaszipHeader extends Structure {
        public int file_source_ID;
        public int global_encoding;
        public int project_ID_GUID_data_1;
        public int project_ID_GUID_data_2;
        public int project_ID_GUID_data_3;
        public byte[] project_ID_GUID_data_4 = new byte[8];
        public int version_major;
        public int version_minor;
        public byte[] system_identifier = new byte[32];
        public byte[] generating_software = new byte[32];
        public int file_creation_day;
        public int file_creation_year;
        public int header_size;
        public int offset_to_point_data;
        public int number_of_variable_length_records;
        public int point_data_format;
        public int point_data_record_length;
        public int number_of_point_records;
        public int number_of_points_by_return[] = new int[5];
        public double x_scale_factor;
        public double y_scale_factor;
        public double z_scale_factor;
        public double x_offset;
        public double y_offset;
        public double z_offset;
        public double max_x;
        public double max_y;
        public double max_z;

        // LAS 1.3 and higher only
        public long start_of_waveform_data_packet_record;

        // LAS 1.4 and higher only
        public long start_of_first_extended_variable_length_record;
        public int number_of_extended_variable_length_records;
        public long extended_number_of_point_records;
        public long extended_number_of_points_by_return[] = new long[15];

        // optional
        public int user_data_in_header_size;
        public IntByReference user_data_in_header;

        // optional
        public LaszipVLR vlrs;

        // optional
        public int user_data_after_header_size;
        public IntByReference user_data_after_header;
    }

    @Structure.FieldOrder({
        "X",
        "Y",
        "Z",
        "intensity",
        "returnNumber",
        "number_of_returns",
        "scan_direction_flag",
        "edge_of_flight_line",
        "classification",
        "synthetic_flag",
        "keypoint_flag",
        "withheld_flag",
        "scan_angle_rank",
        "user_data",
        "point_source_id",
        "extended_scan_angle",
        "extended_point_type",
        "extended_scanner_channel",
        "extended_classification_flags",
        "extended_classifications",
        "extended_return_number",
        "extended_number_of_returns",
        "dummy",
        "gps_time",
        "rgb",
        "wave_packet",
        "num_extra_bytes",
        "extraBytes"
    })
    public static class LaszipPoint extends Structure {
        public int X;
        public int Y;
        public int Z;

        public int intensity;
        public int returnNumber;
        public int number_of_returns;
        public int scan_direction_flag;
        public int edge_of_flight_line;
        public int classification;
        public int synthetic_flag;
        public int keypoint_flag;
        public int withheld_flag;
        public int scan_angle_rank;
        public int user_data;
        public int point_source_id;

        // LAS 1.4 only
        public int extended_scan_angle;
        public int extended_point_type;
        public int extended_scanner_channel;
        public int extended_classification_flags;
        public int extended_classifications;
        public int extended_return_number;
        public int extended_number_of_returns;

        // for 8 byte alignment of the GPS time
        public int dummy[] = new int[7];

        public double gps_time;
        public int rgb[] = new int[4];
        public int wave_packet[] = new int[29];

        public int num_extra_bytes;
        public IntByReference extraBytes;
    }

    public interface CLibrary extends Library {
        LaszipApi.CLibrary INSTANCE = (LaszipApi.CLibrary)
                Native.load("C:\\Users\\luantm\\Workshop\\SCSI\\LAStools\\LASzip\\dll\\LASzip64",
                        LaszipApi.CLibrary.class);

        /*---------------------------------------------------------------------------*/
        /*---------------- DLL functions to manage the LASzip DLL -------------------*/
        /*---------------------------------------------------------------------------*/
        int laszip_get_version(IntByReference version_major, IntByReference version_minor, IntByReference version_revision, IntByReference version_build);
        int laszip_create(PointerByReference pointer);
        int laszip_get_error(PointerByReference pointer, String[] error);
        int laszip_get_warning(PointerByReference pointer, String[] warning);
        int laszip_clean(PointerByReference pointer);
        int laszip_destroy(PointerByReference pointer);

        /*---------------------------------------------------------------------------*/
        /*---------- DLL functions to write and read LAS and LAZ files --------------*/
        /*---------------------------------------------------------------------------*/
        int laszip_get_header_pointer(PointerByReference pointer, LaszipHeader laszipHeader);

        int laszip_get_point_pointer(PointerByReference pointer, LaszipPoint laszipPoint);

        int laszip_set_header(PointerByReference pointer, LaszipHeader laszipHeader);

        int laszip_set_point_type_and_size(PointerByReference pointer, int type, int size);

        int laszip_check_for_integer_overflow(PointerByReference pointer);

        int laszip_auto_offset(PointerByReference pointer);

        int laszip_set_point(PointerByReference pointer, LaszipPoint laszipPoint);

        int laszip_set_coordinates(PointerByReference pointer, double coordinates[]);

        int laszip_get_coordinates(PointerByReference pointer, double coordinates[]);

        int laszip_set_geokeys(PointerByReference pointer, int number, LaszipGeokey[] keyEntries);

        int laszip_set_geodouble_params(PointerByReference pointer, int number, String geoAsciiParams);

        int laszip_add_attribute(PointerByReference pointer, int type, String name, String description, double scale, double offset);

        int laszip_add_vlr(PointerByReference pointer, String userID, int record_id, int record_length_after_header, String description, IntByReference data);

        int laszip_create_spatial_index(PointerByReference pointer, boolean create, int append);

        int laszip_preserve_generating_software(PointerByReference pointer, boolean preserve);

        int laszip_request_native_extension(PointerByReference pointer, boolean request);

        int laszip_set_chunk_size(PointerByReference pointer, int chunkSize);

        int laszip_open_writer(PointerByReference pointer, String file_name, boolean compress);

        int laszip_write_point(PointerByReference pointer);

        int laszip_write_indexed_point(PointerByReference pointer);

        int laszip_update_inventory(PointerByReference pointer);

        int laszip_close_writer(PointerByReference pointer);

        int laszip_exploit_spatial_index(PointerByReference pointer, boolean exploit);

        int laszip_decompress_selective(PointerByReference pointer, int decompress_selective);

        int laszip_open_reader(PointerByReference pointer, String file_name, WinDef.BOOLByReference is_compressed);

        int laszip_has_spatial_index(PointerByReference pointer, WinDef.BOOLByReference is_indexed, WinDef.BOOLByReference is_appended);

        int laszip_inside_rectangle(PointerByReference pointer, double min_x, double min_y, double max_x, double max_y, WinDef.BOOLByReference is_empty);

        int laszip_seek_point(PointerByReference pointer, long index);

        int laszip_read_point(PointerByReference pointer);

        int laszip_read_inside_point(PointerByReference pointer, WinDef.BOOLByReference is_done);

        int laszip_close_reader(PointerByReference pointer);

        /*---------------------------------------------------------------------------*/
        /*---------------- DLL functions to load and unload LASzip ------------------*/
        /*---------------------------------------------------------------------------*/

        int laszip_load_dll();

        int laszip_unload_dll();
    }
    public static void changeString(String s) {
        s = "12";
    }
    public static void main(String[] args) {
        IntByReference versionMajor = new IntByReference();
        IntByReference versionMinor = new IntByReference();
        IntByReference versionRevision = new IntByReference();
        IntByReference versionBuild = new IntByReference();

        CLibrary.INSTANCE.laszip_get_version(versionMajor, versionMinor, versionRevision, versionBuild);
        System.out.println(String.format("LASzip version is: %s.%s.%s.%s",
                versionMajor.getValue(), versionMinor.getValue(), versionRevision.getValue(), versionBuild.getValue()));

        // create the reader
        PointerByReference laszipReader = new PointerByReference();
        if (CLibrary.INSTANCE.laszip_create(laszipReader) != 0) {
            System.out.println("DLL ERROR: creating laszip reader\\n");
            getError(laszipReader);
            return;
        }

        // open the reader
//        WinDef.BOOLByReference is_compressed = new WinDef.BOOLByReference();

        WinDef.BOOLByReference is_compressed = new WinDef.BOOLByReference();
        if (CLibrary.INSTANCE.laszip_open_reader(laszipReader,
                "C:\\Users\\luantm\\Workshop\\SCSI\\LAStools\\LASzip\\example\\5points_14.las",
                is_compressed) != 0) {
            System.out.println("DLL ERROR: open laszip reader failed");
            getError(laszipReader);
            return;
        }

        // get a pointer to the header of the reader that was just populated
        LaszipHeader header = new LaszipHeader();
        if (CLibrary.INSTANCE.laszip_get_header_pointer(laszipReader, header) != 0) {
            System.out.println("DLL ERROR: get header pointer failed");
            getError(laszipReader);
        }

        // how many points does the file have
        System.out.println("Version: " + header.version_major);
        System.out.println("Number points: " + header.number_of_point_records);
        System.out.println("Number points: " + header.extended_number_of_point_records);

    }
    public static void getError(PointerByReference pointer) {
        String[] error = new String[1];
        if (CLibrary.INSTANCE.laszip_get_error(pointer, error) != 0) {
            System.err.println("Cannot get error");
        } else {
            System.err.println(error[0]);
        }
    }
}
