import { Link } from 'expo-router';
import { openBrowserAsync } from 'expo-web-browser';
import { type ComponentProps } from 'react';
import { Platform, GestureResponderEvent } from 'react-native';

type Props = Omit<ComponentProps<typeof Link>, 'href'> & { href: string };

export function ExternalLink({ href, ...rest }: Props) {
  const handlePress = async (event: React.MouseEvent<HTMLAnchorElement> | GestureResponderEvent) => {
    if (Platform.OS !== 'web') {
      event.preventDefault();
      await openBrowserAsync(href);
    }
  };

  // Type assertion to ExternalPathString for external URLs
  const externalHref = href.startsWith('http') ? href : '';

  return (
    <Link
      target="_blank"
      {...rest}
      href={externalHref as ComponentProps<typeof Link>['href']} // Explicit type assertion here
      onPress={handlePress}
    />
  );
}
