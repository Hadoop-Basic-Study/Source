from kafka import KafkaProducer
from kafka.admin import KafkaAdminClient,NewTopic
from json import dumps
import cv2
import time

#토픽 생성
# admin_client = KafkaAdminClient(bootstrap_servers=['localhost:9092','localhost:9093','localhost:9094'],client_id="test_client")
# topic_list = []
# example_topic = NewTopic(name="image_topic",num_partitions=3,replication_factor=3)
# topic_list.append(example_topic)
# admin_client.create_topics(new_topics=topic_list,validate_only=False)

value_serializers = []
value_serializers.append(lambda x: dumps(x).encode('utf-8'))
value_serializers.append(lambda x: cv2.imencode('.png', x))
producer = KafkaProducer(acks=0, compression_type='gzip',
                         bootstrap_servers=['localhost:9092','localhost:9093','localhost:9094'],
                         value_serializer=value_serializers[0])

if __name__=="__main__":
    start = time.time()
    for i in range(3):
        image = cv2.imread("D:/Workspace/Kafka/chunsik.png",cv2.IMREAD_ANYCOLOR)
        ret, buffer = cv2.imencode('.png', image)
        producer.send('image_topic',value=buffer.tobytes())
        producer.flush()
    print('elapsed : ', time.time()- start)
        