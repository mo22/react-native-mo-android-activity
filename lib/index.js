import * as android from './android';
export class AndroidActivity {
    /**
     * native android functions. use with caution
     */
    static android = android;
    /**
     * check if android package is installed
     */
    static async isPackageInstalled(packageId) {
        if (this.android.Module) {
            return await this.android.Module.isPackageInstalled(packageId);
        }
        else {
            throw new Error('platform not supported');
        }
    }
    /**
     * start android activity
     */
    static async startActivity(args) {
        if (this.android.Module) {
            return await this.android.Module.startActivity(args);
        }
        else {
            throw new Error('platform not supported');
        }
    }
    /**
     * start android activity for result
     */
    static async startActivityForResult(args) {
        if (this.android.Module) {
            return await this.android.Module.startActivityForResult(args);
        }
        else {
            throw new Error('platform not supported');
        }
    }
}
//# sourceMappingURL=index.js.map