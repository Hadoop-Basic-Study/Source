from kafka import KafkaConsumer
from json import loads
from PIL import Image
from io import BytesIO

def imageserializer(x):
    stream = BytesIO(x)
    image = Image.open(stream).convert("RGB")
    stream.close()
    return image

# topic, broker list
value_deserializers = []
value_deserializers.append(lambda x: loads(x.decode('utf-8')))
value_deserializers.append(imageserializer)
consumer = KafkaConsumer(
    'image_topic',
     bootstrap_servers=['localhost:9092','localhost:9093','localhost:9094'],
     auto_offset_reset='earliest',
     enable_auto_commit=True,
     group_id='my-group',
     value_deserializer = value_deserializers[1] ,
     consumer_timeout_ms=1000
)

if __name__=="__main__":
    print('[begin] get consumer list')
    for message in consumer:
        # stream = BytesIO(message.value)
        # image = Image.open(stream).convert("RGB")
        # print("Topic: %s, Partition: %d, Offset: %d, Key: %s, Value: %s" % (
        #     message.topic, message.partition, message.offset, message.key, message.value
        # ))
        image=message.value
        image.show()
    print('[end] get consumer list')