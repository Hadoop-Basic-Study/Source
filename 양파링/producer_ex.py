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

def imageserializer(image):
    ret, buffer = cv2.imencode('.png', image)
    buffer = buffer.tobytes()
    return buffer

value_serializers = []
value_serializers.append(lambda x: dumps(x).encode('utf-8'))
value_serializers.append(imageserializer)
producer = KafkaProducer(acks=0, compression_type='gzip',
                         bootstrap_servers=['localhost:9092','localhost:9093','localhost:9094'],
                         value_serializer=value_serializers[1])

if __name__=="__main__":
    start = time.time()
    for i in range(6):
        image = cv2.imread("D:/Workspace/Kafka/image/chunsik.png",cv2.IMREAD_ANYCOLOR)
        #message에 key가 None이면 랜덤하게 partition에 할당되어 저장되고
        #key가 지정되면 같은 키를 가진 message는 같은 partition에 저장된다. 
        producer.send('image_topic',key=b'chunsik',value=image)
        producer.flush()
    print('elapsed : ', time.time()- start)
        