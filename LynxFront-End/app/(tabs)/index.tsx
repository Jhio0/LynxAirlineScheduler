import React from 'react';
import { View, ImageBackground} from 'react-native';
import { Text } from 'react-native-paper';
import FileInputBox from '../../components/FileInputBox'

export default function HomeScreen() {
  return (
    <ImageBackground source={require('../../assets/images/Airline.jpg')} className='flex-1'>
      <View className='flex-1 items-center justify-end'>
        <FileInputBox/>
      </View>
    </ImageBackground>
  );
}

 