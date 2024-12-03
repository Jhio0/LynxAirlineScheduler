import React from 'react';
import { View } from 'react-native';
import { Text } from 'react-native-paper';
import FileInputBox from '../../components/FileInputBox'

export default function HomeScreen() {
  return (
    <View className='flex items-center justify-center h-screen'>
      <FileInputBox/>
    </View>
  );
}

 