package io.anemos.protobeam.convert.nodes.tablerow;

import com.google.api.services.bigquery.model.TableRow;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Message;
import io.anemos.protobeam.convert.nodes.AbstractConvert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class RepeatedConvert extends AbstractConvert<Object, TableRow, Map<String, Object>> {

    private AbstractConvert field;

    public RepeatedConvert(Descriptors.FieldDescriptor descriptor, AbstractConvert field) {
        super(descriptor);
        this.field = field;
    }

    @Override
    public Object convert(Object in) {
        return in;
    }

    @Override
    public void convert(Message message, TableRow row) {
        List tableCells = new ArrayList<>();
        ((List) message.getField(descriptor)).forEach(
                obj -> tableCells.add(field.convert(obj))
        );
        row.set(descriptor.getName(), tableCells);
    }

    @Override
    public void convertToProto(Message.Builder builder, Map row) {
        List list = (List) row.get(descriptor.getName());
        list.forEach(
                obj -> builder.addRepeatedField(descriptor, field.convertFrom(obj))
        );
    }

}
