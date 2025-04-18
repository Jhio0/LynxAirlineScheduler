import React, { useState } from "react";
import { View, Text, TouchableOpacity, Button, Alert, StyleSheet } from "react-native";
import * as DocumentPicker from "expo-document-picker";
import axios from "axios";

const FileInputBox = () => {
  const [selectedFile, setSelectedFile] = useState(null);
  const [fileUri, setFileUri] = useState(null);

  const handleFilePick = async () => {
  try {
    const file = await DocumentPicker.getDocumentAsync({
      type: "*/*", // Specify file types if needed
      copyToCacheDirectory: true, // Ensure the file is copied to cache
    });

    // Check if the file was successfully picked
    if (file.assets && file.assets.length > 0) {
      const pickedFile = file.assets[0];  // Pick the first file from the assets array
      setSelectedFile(pickedFile.name);
      setFileUri(pickedFile.uri);
      console.log("File picked:", pickedFile); // Debug log
    } else {
      console.error("File picking was canceled or failed.");
    }
  } catch (error) {
    console.error("File pick error:", error);
    Alert.alert("Error", "Unable to pick file.");
  }
};

const handleUpload = async () => {
  if (!fileUri) {
    Alert.alert("No file selected", "Please select a file before uploading.");
    return;
  }

  const formData = new FormData();
  formData.append("file", {
    uri: fileUri.startsWith('file://') ? fileUri : 'file://' + fileUri, // Ensure the URI is prefixed with file://
    name: selectedFile, // Ensure this is the correct file name
    type: "application/vnd.ms-excel", // Adjust MIME type as needed
  });

  // Log formData to inspect its structure
  formData.forEach((value, key) => {
    console.log(key, value);
  });

  try {
    const response = await axios.post("http://192.168.1.109:3001/api/upload/excel", formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
    console.log("Upload response:", response.data);
    Alert.alert("Success", "File uploaded successfully!");
  } catch (error) {
    console.error("File upload error:", error.response || error.message);
    Alert.alert("Error", "Failed to upload file.");
  }
};


  return (
    <View style={styles.container}>
      {/* <TouchableOpacity>
        <Image 
          source={require('../assets/images/Airline.jpg')} 
          style={styles.image}
        />
      </TouchableOpacity> */}

        <TouchableOpacity style={styles.box} onPress={handleFilePick}>
          <Text style={styles.text}>
            {selectedFile ? `Selected File: ${selectedFile}` : "Tap to upload a file"}
          </Text>
        </TouchableOpacity>
        <View className="mt-5">
          <Button className="bg-black" title="Upload File" onPress={handleUpload} />\
        </View>

    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    alignItems: "center",
    justifyContent: "center",
    marginVertical: 50
  },
  box: {
    width: 250,
    height: 100,
    borderWidth: 2,
    borderColor: "#aaa",
    borderStyle: "dotted",
    borderRadius: 10,
    alignItems: "center",
    justifyContent: "center",
    backgroundColor: "transparent",
  },
  text: {
    color: "#ffff",
    fontSize: 16,
    textAlign: "center",
  },
});

export default FileInputBox;
