import React from 'react';
import { View, StyleSheet } from 'react-native';

import { CommonActions } from '@react-navigation/native';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import { Text, BottomNavigation } from 'react-native-paper';
import Icon from 'react-native-vector-icons/MaterialCommunityIcons';

import TabTwoScreen from './explore';
import HomeScreen from './index';
import CalendarTab from '../(tabs)/calendarTab';
const Tab = createBottomTabNavigator();
export default function TabLayout() {
  return (
    
    <Tab.Navigator
      screenOptions={{
        headerShown: false,
      }}
      
      tabBar={({ navigation, state, descriptors, insets }) => (
        <BottomNavigation.Bar
          navigationState={state}
         safeAreaInsets={insets}
          onTabPress={({ route, preventDefault }) => {
            const event = navigation.emit({
              type: 'tabPress',
              target: route.key,
              canPreventDefault: true,
            });

            if (event.defaultPrevented) {
              preventDefault();
            } else {
             navigation.dispatch({
                ...CommonActions.navigate(route.name, route.params),
                target: state.key,
              });
            }
          }}
          renderIcon={({ route, focused, color }) => {
            const { options } = descriptors[route.key];
            if (options.tabBarIcon) {
              return options.tabBarIcon({ focused, color, size: 24 });
            }

            return null;
          }}
          getLabelText={({ route }) => {
            const { options } = descriptors[route.key];
            const label =
              options.tabBarLabel !== undefined
                ? options.tabBarLabel
                : options.title !== undefined
                ? options.title
                // @ts-ignore
                : route.title; // Use route.name instead of route.title

            return label; // Ensure the return type is a string
          }}

        />
      )}
      
    >
      
      <Tab.Screen
        name="Home"
        component={HomeScreen}
        options={{
          tabBarLabel: 'Home',
          tabBarIcon: ({ color, size }) => {
            return <Icon name="home" size={size} color={color} />;
          },
        }}
      />

    <Tab.Screen
        name="Calendar"
        component={CalendarTab}
        options={{
          tabBarLabel: 'Calendar',
          tabBarIcon: ({ color, size }) => {
            return <Icon name="calendar" size={size} color={color} />;
          },
        }}
      />
      
      <Tab.Screen
        name="Settings"
        component={TabTwoScreen}
        options={{
          tabBarLabel: 'Settings',
          tabBarIcon: ({ color, size }) => {
            return <Icon name="cog" size={size} color={color} />;
          },
        }}
      />

      
    </Tab.Navigator>
  );
}
