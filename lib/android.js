import { NativeModules, Platform } from 'react-native';
export const Module = (Platform.OS === 'android') ? NativeModules.ReactNativeMoAndroidActivity : undefined;
//# sourceMappingURL=android.js.map